package io.alpenglow.artoo.lance.scope;

import io.alpenglow.artoo.lance.func.TryConsumer1;
import io.alpenglow.artoo.lance.func.TryFunction1;
import io.alpenglow.artoo.lance.func.TrySupplier1;


import static io.alpenglow.artoo.lance.scope.Default.Nothing;

public interface Let<T> {
  static <T> Let<T> lazy(final TrySupplier1<T> supplier) {
    return new Let.Lazy<>(supplier);
  }

  <R> R let(final TryFunction1<? super T, ? extends R> func);
  default Let<T> get(final TryConsumer1<? super T> func) {
    let(it -> {
      func.accept(it);
      return null;
    });
    return this;
  }

  sealed interface Readonly<T> extends Let<T> permits Let.Lazy {}

  final class Lazy<T> implements Readonly<T> {
    private final TrySupplier1<? extends T> suppl;
    private volatile Object value;

    private Lazy(final TrySupplier1<T> suppl) {
      this(
        suppl,
        Nothing
      );
    }

    private Lazy(final TrySupplier1<? extends T> suppl, final Object value) {
      this.suppl = suppl;
      this.value = value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> R let(final TryFunction1<? super T, ? extends R> func) {
      final var unsyncd = value;
      if (Nothing.equals(unsyncd)) {
        synchronized (this) {
          final var syncd = value;
          if (Nothing.equals(syncd)) {
            final var supplied = suppl.get();
            if (supplied != null)
              this.value = supplied;
            return func.apply(supplied);
          } else {
            return func.apply((T) syncd);
          }
        }
      } else {
        return func.apply((T) unsyncd);
      }
    }
  }
}