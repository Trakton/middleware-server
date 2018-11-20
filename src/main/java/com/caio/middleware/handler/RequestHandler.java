package com.caio.middleware.handler;

import java.io.Closeable;
import java.io.IOException;

public interface RequestHandler extends Closeable {
  byte[] receive() throws IOException;

  void send(byte[] message) throws IOException;
}
