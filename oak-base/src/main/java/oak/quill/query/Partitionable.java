package oak.quill.query;

import oak.func.pre.Predicate1;
import oak.quill.Structable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

import static oak.type.Nullability.nonNullable;

public interface Partitionable<T> extends Structable<T> {
  default Queryable<T> skip(final int until) {
    return new Skip<>(this, until);
  }
  default Queryable<T> skipWhile(final Predicate1<? super T> filter) {
    return new SkipWhile<>(this, nonNullable(filter, "filter"));
  }
  default Queryable<T> take(final int until) {
    return new Take<>(this, until);
  }
  default Queryable<T> takeWhile(final Predicate1<? super T> filter) {
    return new TakeWhile<>(this, nonNullable(filter, "filter"));
  }
}

final class Skip<S> implements Queryable<S> {
  private final Structable<S> structable;
  private final int until;

  @Contract(pure = true)
  Skip(final Structable<S> structable, final int until) {
    this.structable = structable;
    this.until = until;
  }

  @NotNull
  @Override
  public final Iterator<S> iterator() {
    var skip = 0;
    var seq = new ArrayList<S>();
    for (final var it : structable) if (skip++ >= until) seq.add(it);
    return seq.iterator();
  }
}

final class SkipWhile<S> implements Queryable<S> {
  private final Structable<S> structable;
  private final Predicate1<? super S> filter;

  @Contract(pure = true)
  SkipWhile(final Structable<S> structable, final Predicate1<? super S> filter) {
    this.structable = structable;
    this.filter = filter;
  }

  @NotNull
  @Override
  public final Iterator<S> iterator() {
    var s = new ArrayList<S>();
    var keepSkipping = true;
    for (var it : structable) {
      if (!filter.test(it) || !keepSkipping) {
        s.add(it);
        keepSkipping = false;
      }
    }
    return s.iterator();
  }
}

final class Take<S> implements Queryable<S> {
  private final Structable<S> source;
  private final int until;

  @Contract(pure = true)
  Take(final Structable<S> source, final int until) {
    this.source = source;
    this.until = until;
  }

  @NotNull
  @Override
  public final Iterator<S> iterator() {
    var take = 0;
    var seq = new ArrayList<S>();
    for (final var it : source) if (take++ < until) seq.add(it);
    return seq.iterator();
  }
}

final class TakeWhile<S> implements Queryable<S> {
  private final Structable<S> some;
  private final Predicate1<? super S> expression;

  @Contract(pure = true)
  TakeWhile(final Structable<S> some, final Predicate1<? super S> expression) {
    this.some = some;
    this.expression = expression;
  }

  @NotNull
  @Override
  public final Iterator<S> iterator() {
    if (!expression.test(some.iterator().next())) return some.iterator();

    var s = new ArrayList<S>();
    var keepTaking = true;
    for (var iterator = some.iterator(); iterator.hasNext() && keepTaking;) {
      var it = iterator.next();
      if (expression.test(it)) {
        s.add(it);
      } else {
        keepTaking = false;
      }
    }
    return s.iterator();
  }
}
