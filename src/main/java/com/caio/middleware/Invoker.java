package com.caio.middleware;

import com.caio.middleware.handler.ClientRequestHandler;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import io.mid_player.EventStreaming;
import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

@RequiredArgsConstructor
public class Invoker implements Closeable {
  private final MiddlewareState state;
  private final Map<Integer, Topic> topicMap;
  private final ClientRequestHandler crh;
  private ConcurrentMap<Integer, BlockingQueue<ByteString>> queueMap =
      new ConcurrentHashMap<>();
  private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
  private final Marshaller marshaller;
  private Thread receiver;
  private Thread caller;
  private BlockingQueue<Integer> processingQueue;

  void start() {
    queueMap.putAll(
        topicMap
            .keySet()
            .stream()
            .map((key) -> Pair.of(key, new ArrayBlockingQueue<ByteString>(5)))
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue)));
    this.processingQueue = new ArrayBlockingQueue<Integer>(topicMap.size(), true, topicMap.keySet());
    receiver = new Thread(this::receiver);
    receiver.start();
    caller = new Thread(this::caller);
    caller.start();
  }

  private void receiver() {
    while (state.isStarted()) {
      try {
        byte[] msg = crh.receive();
        final EventStreaming incomingEvent = marshaller.unmarshallEventStreaming(msg);

        final BlockingQueue<ByteString> queue = queueMap.get(incomingEvent.getTopicId());

        queue.put(incomingEvent.getPayload());
      } catch (IOException | InterruptedException e) {
        if(!state.isStopped()) throw new RuntimeException(e);
      }
    }
  }

  private void caller() {
    while (state.isStarted()) {
      try {
        final Integer topicId = processingQueue.take();
        executor.submit(() -> this.process(topicId, queueMap.get(topicId).poll()));
      } catch (InterruptedException e) {
        if(!state.isStopped()) throw new RuntimeException(e);
      }
    }
  }

  private void process(int topicId, ByteString message) {
    try {
      Topic topic = topicMap.get(topicId);

      if(message != null) topic.process(message);
    } catch (InvalidProtocolBufferException e){
      throw new RuntimeException(e);
    } finally {
      try {
        processingQueue.put(topicId);
      } catch (InterruptedException e) {
        if(!state.isStopped()) throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void close() {
    try {
      receiver.interrupt();
      caller.interrupt();
      caller.join();
      receiver.join();
      executor.shutdown();
      if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
        throw new RuntimeException("Thread executor taking too long");
      }
    } catch (InterruptedException iE) {
      throw new RuntimeException("Failed to stop threads", iE);
    }
  }
}
