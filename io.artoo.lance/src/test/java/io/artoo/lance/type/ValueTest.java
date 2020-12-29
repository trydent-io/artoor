package io.artoo.lance.type;

import io.artoo.lance.func.Action;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ValueTest {
  @Test
  void shouldGetValue() throws InterruptedException {
    final var value = Let.lazy(() -> 0);

    final var pool = Executors.newFixedThreadPool(5);

    final var atomic = new AtomicInteger(0);
    final var threads = List.of(
      () -> atomic.set(value.let(it -> atomic.get() + 1)),
      () -> atomic.set(value.let(it -> atomic.get() + 2)),
      () -> atomic.set(value.let(it -> atomic.get() + 3)),
      () -> atomic.set(value.let(it -> atomic.get() + 4)),
      () -> atomic.set(value.let(it -> atomic.get() + 5)),
      () -> atomic.set(value.let(it -> atomic.get() + 6)),
      () -> atomic.set(value.let(it -> atomic.get() + 7)),
      () -> atomic.set(value.let(it -> atomic.get() + 8)),
      () -> atomic.set(value.let(it -> atomic.get() + 9)),
      (Action) () -> atomic.set(value.let(it -> atomic.get() + 10))
    );

    pool.invokeAll(threads);
    final var terminated = pool.awaitTermination(10, SECONDS);

    assertThat(atomic.get()).isEqualTo(55);
  }
}
