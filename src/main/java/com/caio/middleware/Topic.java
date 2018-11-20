package com.caio.middleware;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import io.mid_player.EventProduction;
import java.io.IOException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Topic<T extends Message> {
  private Callable<T> callable;
  private final MiddlewareState state;
  private final Requestor requestor;
  @Getter private final int id;
  private final Parser<T> parser;

  public void produce(T value) throws IOException, MiddlewareException {
    if (!state.isStarted()) throw new MiddlewareException();
    final EventProduction event =
        EventProduction.newBuilder()
            .setTopicId(id)
            .setPayload(value.toByteString())
            .setTimestamp(System.currentTimeMillis())
            .build();
    requestor.send(event);
  }

  public void onMessage(Callable<T> callable) throws MiddlewareException {
    if (!state.isCreating()) throw new MiddlewareException();
    this.callable = callable;
  }

  void process(ByteString msg) throws InvalidProtocolBufferException {
    final T value = parser.parseFrom(msg);
    callable.process(value);
  }

  boolean hasSubscription() {
    return this.callable != null;
  }
}
