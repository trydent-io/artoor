package io.alpenglow.artoo.lance.query.closure;

import io.alpenglow.artoo.lance.func.TryPredicate1;
import io.alpenglow.artoo.lance.query.Closure;

public final class Single<T> implements Closure<T, T> {
  enum NoSingle {Found}

  private Object single = null;
  private final TryPredicate1<? super T> where;

  public Single(final TryPredicate1<? super T> where) {
    assert where != null;
    this.where = where;
  }

  @Override
  public final T tryApply(final T element) throws Throwable {
    if (where.invoke(element) && single == null) {
      single = element;
    } else if (where.invoke(element)) {
      single = NoSingle.Found;
    }

    //noinspection unchecked
    return NoSingle.Found.equals(single)
      ? null
      : (T) single;
  }
}