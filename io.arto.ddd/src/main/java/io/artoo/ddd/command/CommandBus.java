package io.artoo.ddd.command;

import io.artoo.ddd.Domain;
import io.artoo.ddd.util.Lettering;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

import java.util.function.Consumer;

import static io.vertx.core.Future.succeededFuture;

public interface CommandBus {
  <C extends Domain.Command> Future<Void> send(C command);

  <C extends Domain.Command> CommandBus when(Class<C> command, Consumer<C> handler);

  static CommandBus create(EventBus eventBus) {
    return new InMemory(eventBus);
  }
}

final class InMemory implements CommandBus, Lettering {
  private final EventBus eventBus;

  InMemory(final EventBus eventBus) {this.eventBus = eventBus;}

  @Override
  public <C extends Domain.Command> Future<Void> send(final C command) {
    eventBus.publish(asKebabCase(command.getClass()), JsonObject.mapFrom(command));
    return succeededFuture();
  }

  @Override
  public <C extends Domain.Command> CommandBus when(final Class<C> command, final Consumer<C> handler) {
    eventBus.<JsonObject>localConsumer(asKebabCase(command), message -> handler.accept(message.body().mapTo(command)));
    return this;
  }
}

