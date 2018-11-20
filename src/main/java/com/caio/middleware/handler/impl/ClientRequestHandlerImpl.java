package com.caio.middleware.handler.impl;

import static java.nio.ByteOrder.BIG_ENDIAN;

import com.caio.middleware.handler.ClientRequestHandler;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ClientRequestHandlerImpl implements ClientRequestHandler {
  private final Socket socket;
  private final DataInputStream dataInputStream;
  private final DataOutputStream dataOutputStream;

  public ClientRequestHandlerImpl(String host, int port) throws IOException {
    this.socket = new Socket(host, port);
    this.dataInputStream = new DataInputStream(socket.getInputStream());
    this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
  }

  @Override
  public byte[] receive() throws IOException {
    byte[] sizeBuf = new byte[4];
    dataInputStream.readFully(sizeBuf, 2, 2);
    int size = ByteBuffer.wrap(sizeBuf).order(BIG_ENDIAN).getInt();
    byte[] messageBuf = new byte[size];
    dataInputStream.readFully(messageBuf);
    return messageBuf;
  }

  @Override
  public void send(byte[] message) throws IOException {
    int size = message.length;
    byte[] sizeBuf = new byte[4];
    ByteBuffer.wrap(sizeBuf).order(BIG_ENDIAN).putInt(size);
    byte[] bytes = new byte[2 + size];
    ByteBuffer.wrap(bytes).put(sizeBuf, 2, 2).put(message);
    dataOutputStream.write(bytes);
    dataOutputStream.flush();
  }

  @Override
  public void close() throws IOException {
    dataInputStream.close();
    dataOutputStream.close();
    socket.close();
  }
}
