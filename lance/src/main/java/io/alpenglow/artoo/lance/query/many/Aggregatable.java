package io.alpenglow.artoo.lance.query.many;

import io.alpenglow.artoo.lance.func.TryFunction2;
import io.alpenglow.artoo.lance.func.TryFunction1;
import io.alpenglow.artoo.lance.func.TryPredicate1;
import io.alpenglow.artoo.lance.func.tail.Aggregate;
import io.alpenglow.artoo.lance.query.One;

public interface Aggregatable<T> extends Countable<T>, Summable<T>, Averageable<T>, Extremable<T> {
  default <A, R> One<A> aggregate(final A seed, final TryPredicate1<? super T> where, final TryFunction1<? super T, ? extends R> select, final TryFunction2<? super A, ? super R, ? extends A> aggregate) {
    return () -> cursor().map(rec(Aggregate.with(seed, where, select, aggregate))).keepNull();
  }

  default <A, R> One<A> aggregate(final A seed, final TryFunction1<? super T, ? extends R> select, final TryFunction2<? super A, ? super R, ? extends A> aggregate) {
    return aggregate(seed, it -> true, select, aggregate);
  }

  default <A, R> One<A> aggregate(final TryFunction1<? super T, ? extends R> select, final TryFunction2<? super A, ? super R, ? extends A> aggregate) {
    return aggregate(null, it -> true, select, aggregate);
  }

  default <A> One<A> aggregate(final A seed, final TryFunction2<? super A, ? super T, ? extends A> aggregate) {
    return aggregate(seed, it -> true, it -> it, aggregate);
  }

  default One<T> aggregate(final TryFunction2<? super T, ? super T, ? extends T> aggregate) {
    return aggregate(null, it -> true, it -> it, aggregate);
  }

}
