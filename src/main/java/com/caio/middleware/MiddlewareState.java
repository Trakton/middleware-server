package com.caio.middleware;

import java.util.concurrent.atomic.AtomicReference;

public class MiddlewareState {
  private AtomicReference<State> state;

  MiddlewareState() {
    this.state = new AtomicReference<State>();
    this.state.set(State.CREATING);
  }

  boolean isStarted() {
    return State.STARTED.equals(this.state.get());
  }

  boolean isCreating() {
    return State.CREATING.equals(this.state.get());
  }

  void start() throws MiddlewareException {
    if (!State.CREATING.equals(this.state)) throw new MiddlewareException();
    this.state.set(State.STARTED);
  }

  void stop() {
    this.state.set(State.STOPED);
  }

  private enum State {
    CREATING,
    STARTED,
    STOPED
  }
}
