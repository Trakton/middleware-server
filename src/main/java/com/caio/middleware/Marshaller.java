package com.caio.middleware;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import io.mid_player.EventStreaming;
import io.mid_player.EventSubscriptionResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Marshaller {
  byte[] marshall(Message msg) {
    return msg.toByteArray();
  }

  public EventSubscriptionResponse unmarshallEventSubscriptinResponse(byte[] msg) {
    try {
      return EventSubscriptionResponse.parseFrom(msg);
    } catch (InvalidProtocolBufferException e) {
      throw new RuntimeException(e);
    }
  }

  public EventStreaming unmarshallEventStreaming(byte[] msg) {
    try {
      return EventStreaming.parseFrom(msg);
    } catch (InvalidProtocolBufferException e) {
      throw new RuntimeException(e);
    }
  }
}
