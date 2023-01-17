package re.artoo.lance.query.closure;

import re.artoo.lance.func.TryFunction2;
import re.artoo.lance.tuple.Pair;
import re.artoo.lance.tuple.Tuple;

public final class OfTwoTypes<A, B, X, Y> implements TryFunction2<A, B, Pair<X, Y>> {
  private final Class<? extends X> type1;
  private final Class<? extends Y> type2;

  public OfTwoTypes(final Class<? extends X> type1, final Class<? extends Y> type2) {
    assert type1 != null && type2 != null;
    this.type1 = type1;
    this.type2 = type2;
  }

  @Override
  public final Pair<X, Y> invoke(final A first, final B second) {
    return type1.isInstance(first) && type2.isInstance(second) ? Tuple.of(type1.cast(first), type2.cast(second)) : null;
  }
}