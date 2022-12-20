package io.alpenglow.artoo.lance.query.many.pairs;

import io.alpenglow.artoo.lance.Queryable;
import io.alpenglow.artoo.lance.func.TryPredicate2;
import io.alpenglow.artoo.lance.func.TryPredicate3;
import io.alpenglow.artoo.lance.query.Many;
import io.alpenglow.artoo.lance.query.closure.OfTwoTypes;
import io.alpenglow.artoo.lance.query.closure.Where;

public interface Filterable<A, B> extends Queryable.OfTwo<A, B> {
  default Many.Pairs<A, B> where(final TryPredicate2<? super A, ? super B> where) {
    return where((index, first, second) -> where.invoke(first, second));
  }

  default Many.Pairs<A, B> where(final TryPredicate3<? super Integer, ? super A, ? super B> where) {
    return () -> cursor().map(new Where<>((index, pair) -> where.invoke(index, pair.first(), pair.second())));
  }

  default <X, Y> Many.Pairs<X, Y> ofTypes(final Class<? extends X> first, final Class<? extends Y> second) {
    return () -> cursor().map(it -> new OfTwoTypes<A, B, X, Y>(first, second).invoke(it.first(), it.second()));
  }
}
