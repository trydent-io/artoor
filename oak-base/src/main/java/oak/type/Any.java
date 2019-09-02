package oak.type;

import oak.collect.cursor.Cursor;
import oak.quill.single.Nullable;
import oak.func.con.Consumer1;
import oak.func.exe.Executable;
import oak.func.fun.Function1;
import oak.func.pre.Predicate1;
import oak.func.sup.Supplier1;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;
import static oak.quill.single.Nullable.of;

public interface Any<T> extends Iterable<T>, Supplier1<T> {

  static <T, R> R nonNull(final T any, final Function1<T, R> then) {
    return nonNull(any, then, null);
  }
  static <T, R> R nonNull(final T any, final Function1<T, R> then, final String message) {
    if (isNull(any)) throw new IllegalStateException(requireNonNullElse(message, "Any is null"));

    return then.apply(any);
  }
  static <T, R> R nonNullOrElse(final T any, final Function1<T, R> then, final Supplier1<R> otherwise) {
    return Objects.nonNull(any)
      ? requireNonNull(then, "Then is null").apply(any)
      : requireNonNull(otherwise, "Otherwise is null").get();
  }

  default Nullable<T> filter(final Predicate1<T> predicate) {
    return of(get()).where(predicate);
  }

  default void eventually(final Consumer1<T> then) {
    eventually(then, () -> {});
  }

  default void eventually(final Consumer1<T> then, final Executable eventually) {
    if (this.iterator().hasNext()) {
      for (final var value : this) requireNonNull(then, "Then is null").accept(value);
    } else {
      requireNonNull(eventually, "Eventually is null").run();
    }
  }

  @NotNull
  @Override
  default Iterator<T> iterator() {
    return Cursor.ofNullable(this.get());
  }
}
