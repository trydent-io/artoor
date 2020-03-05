package oak.query.many.internal;

import oak.cursor.Cursor;
import oak.func.$2.IntCons;
import oak.func.Func;
import oak.query.One;
import oak.query.Queryable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public final class Extremum<T, R> implements Queryable<R> {
  private final Queryable<T> queryable;
  private final IntCons<? super T> peek;
  private final int extreme;
  private final Func<? super R, Comparable<? super R>> comparing;
  private final Func<? super T, ? extends R> select;

  @Contract(pure = true)
  public Extremum(
    final Queryable<T> queryable,
    final IntCons<? super T> peek,
    final int extreme,
    final Func<? super R, Comparable<? super R>> comparing,
    final Func<? super T, ? extends R> select
  ) {
    this.queryable = queryable;
    this.peek = peek;
    this.extreme = extreme;
    this.comparing = comparing;
    this.select = select;
  }

  @NotNull
  @Override
  public final Iterator<R> iterator() {
    R result = null;
    var index = 0;
    for (var iterator = queryable.iterator(); iterator.hasNext(); index++) {
      var it = iterator.next();
      peek.acceptInt(index, it);
      if (it != null) {
        final var mapped = select.apply(it);
        if (result == null || mapped != null && comparing.apply(mapped).compareTo(result) == extreme)
          result = mapped;
      }
    }
    return Cursor.of(result);
  }
}