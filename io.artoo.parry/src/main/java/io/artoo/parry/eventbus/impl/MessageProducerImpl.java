/*
 * Copyright (c) 2011-2019 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */

package io.artoo.parry.eventbus.impl;

import io.artoo.parry.ContextInternal;
import io.artoo.parry.Vertx;
import io.artoo.parry.VertxInternal;
import io.artoo.parry.async.AsyncResult;
import io.artoo.parry.async.Future;
import io.artoo.parry.async.Handler;
import io.artoo.parry.async.Promise;
import io.artoo.parry.eventbus.DeliveryOptions;
import io.artoo.parry.eventbus.MessageProducer;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class MessageProducerImpl<T> implements MessageProducer<T> {

  private final Vertx vertx;
  private final EventBusImpl bus;
  private final boolean send;
  private final String address;
  private DeliveryOptions options;

  public MessageProducerImpl(Vertx vertx, String address, boolean send, DeliveryOptions options) {
    this.vertx = vertx;
    this.bus = (EventBusImpl) vertx.eventBus();
    this.address = address;
    this.send = send;
    this.options = options;
  }

  @Override
  public synchronized MessageProducer<T> deliveryOptions(DeliveryOptions options) {
    this.options = options;
    return this;
  }

  @Override
  public Future<Void> write(T body) {
    Promise<Void> promise = ((VertxInternal)vertx).getOrCreateContext().promise();
    write(body, promise);
    return promise.future();
  }

  @Override
  public void write(T body, Handler<AsyncResult<Void>> handler) {
    Promise<Void> promise = null;
    if (handler != null) {
      promise = ((VertxInternal)vertx).getOrCreateContext().promise(handler);
    }
    write(body, promise);
  }

  private void write(T data, Promise<Void> handler) {
    MessageImpl msg = bus.createMessage(send, address, options.getHeaders(), data, options.getCodecName());
    bus.sendOrPubInternal(msg, options, null, handler);
  }

  @Override
  public String address() {
    return address;
  }

  @Override
  public Future<Void> close() {
    return ((ContextInternal)vertx.getOrCreateContext()).succeededFuture();
  }

  @Override
  public void close(Handler<AsyncResult<Void>> handler) {
    Future<Void> fut = close();
    if (handler != null) {
      fut.onComplete(handler);
    }
  }
}
