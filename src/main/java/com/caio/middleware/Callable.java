package com.caio.middleware;

import com.google.protobuf.Message;

public interface Callable<T extends Message> {

  public void process(T message);
}
