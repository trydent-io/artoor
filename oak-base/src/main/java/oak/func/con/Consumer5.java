package oak.func.con;

import oak.func.Functional;

import static java.util.Objects.requireNonNull;

@FunctionalInterface
public interface Consumer5<T1, T2, T3, T4, T5> extends Functional.Con {
  void accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);

  default Consumer5<T1, T2, T3, T4, T5> then(Consumer5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5> after) {
    requireNonNull(after, "After-consumer can't ben null.");
    return (t1, t2, t3, t4, t5) -> { accept(t1, t2, t3, t4, t5); after.accept(t1, t2, t3, t4, t5); };
  }
}