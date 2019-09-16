package oak.quill;

import oak.quill.query.Queryable;
import oak.quill.single.Nullable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static oak.type.Nullability.nonNullableState;

@FunctionalInterface
public interface Structable<T> extends Iterable<T> {
  @NotNull
  @Contract(pure = true)
  static <R> Queryable<R> asQueryable(final Structable<R> structable) {
    return () -> nonNullableState(structable, "structable").iterator();
  }

  @NotNull
  @Contract(pure = true)
  static <R> Nullable<R> asNullable(final Structable<R> structable) {
    return () -> nonNullableState(structable, "structable").iterator().next();
  }
}
