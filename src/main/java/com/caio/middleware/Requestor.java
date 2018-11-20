package com.caio.middleware;

import com.caio.middleware.handler.ClientRequestHandler;
import com.google.protobuf.Message;
import io.mid_player.EventSubscriptionResponse;
import java.io.Closeable;
import java.io.IOException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Requestor implements Closeable {
  private final ClientRequestHandler crh;
  private final Marshaller marshaller;

  EventSubscriptionResponse start(Message inv) throws IOException {
    byte[] msgMarshalled = marshaller.marshall(inv);
    crh.send(msgMarshalled);

    byte[] msgToBeUnmarshalled = crh.receive();

    return marshaller.unmarshallEventSubscriptinResponse(msgToBeUnmarshalled);
  }

  void send(Message inv) throws IOException {
    byte[] msgMarshalled = marshaller.marshall(inv);

    crh.send(msgMarshalled);
  }

  public void close() throws IOException {}
}
