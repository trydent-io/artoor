package artoo.query.many.internal;

import artoo.cursor.$2.Cursor;
import artoo.func.$2.Pred;
import artoo.query.$2.Many;
import artoo.query.Queryable;
import artoo.query.many.Joining;
import artoo.union.$2.Union;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class Join<O, I> implements Joining<O, I> {
  private final Queryable<O> first;
  private final Queryable<I> second;

  @Contract(pure = true)
  public Join(final Queryable<O> first, final Queryable<I> second) {
    this.first = first;
    this.second = second;
  }

  @NotNull
  @Contract(pure = true)
  @Override
  public final Many<O, I> on(final Pred<? super O, ? super I> on) {
    return () -> {
      final var array = new ArrayList<Union<O, I>>();
      for (final var o : first) {
        for (final var i : second) {
          if (on.test(o, i))
            array.add(Union.of(o, i));
        }
      }
      return Cursor.from(array.iterator());
    };
  }
}