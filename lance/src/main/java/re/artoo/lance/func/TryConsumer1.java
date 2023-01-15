package re.artoo.lance.func;

import java.util.function.Consumer;

@FunctionalInterface
public interface TryConsumer1<A> extends Consumer<A> {
  void invoke(A a) throws Throwable;

  @Override
  default void accept(A a) {
    try {
      invoke(a);
    } catch (Throwable throwable) {
      throw new InvokeException(throwable);
    }
  }
}
