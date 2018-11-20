package com.caio.middleware;

import com.caio.middleware.handler.ClientRequestHandler;
import com.caio.middleware.handler.impl.ClientRequestHandlerImpl;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import io.mid_player.EventSubscription;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MiddlewareProxy implements Closeable {
  private Requestor requestor;
  private MiddlewareState state;
  private int channel;
  private Map<Integer, Topic> topicMap;
  private Invoker invoker;
  private ClientRequestHandler clientRequestHandler;

  public MiddlewareProxy(String host, int port, int chanel) throws IOException {
    final Marshaller marshaller = new Marshaller();
    this.channel = chanel;
    this.clientRequestHandler = new ClientRequestHandlerImpl(host, port);
    this.requestor = new Requestor(clientRequestHandler, marshaller);
    this.topicMap = new HashMap<>();
    this.state = new MiddlewareState();
    this.invoker = new Invoker(this.state, this.topicMap, clientRequestHandler, marshaller);
  }

  public void start() throws MiddlewareException, IOException {
    this.state.start();

    final EventSubscription eventSubscription =
        EventSubscription.newBuilder()
            .addAllTopicIds(
                topicMap
                    .values()
                    .stream()
                    .filter(Topic::hasSubscription)
                    .map(Topic::getId)
                    .collect(Collectors.toList()))
            .setChannelId(channel)
            .build();

    this.requestor.start(eventSubscription);
    this.invoker.start();
  }

  public <T extends Message> Topic<T> createTopic(int topicId, Parser<T> parser)
      throws MiddlewareException {
    if (topicMap.get(topicId) != null) throw new MiddlewareException();
    final Topic<T> topic = new Topic<>(this.state, this.requestor, topicId, parser);
    topicMap.put(topicId, topic);
    return topic;
  }

  @SuppressWarnings("unchecked")
  public <T extends Message> Topic<T> getTopic(int topicId) throws MiddlewareException {
    return topicMap.get(topicId);
  }

  @Override
  public void close() throws IOException {
    this.state.stop();
    requestor.close();
    invoker.close();
    clientRequestHandler.close();
  }
}
