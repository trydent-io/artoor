package oak.func.sup;

import oak.func.Functional;
import oak.func.fun.Function1;

import java.util.function.Supplier;

@FunctionalInterface
public interface Supplier1<T> extends Supplier<T>, Function1<Void, T>, Functional.Sup {
  static <T> Supplier1<T> none() { return () -> null; }

  @Override
  default T apply(final Void none) {
    return get();
  }
}
