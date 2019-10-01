package oak.quill.tuple;

import oak.func.con.Consumer3;
import oak.func.fun.Function3;
import oak.func.pre.Predicate3;
import oak.quill.single.Nullable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static oak.type.Nullability.nonNullable;

public interface Tuple3<V1, V2, V3> extends Tuple {
  <R> Nullable<R> select(Function3<? super V1, ? super V2, ? super V3, ? extends R> map);
  Tuple3<V1, V2, V3> peek(Consumer3<? super V1, ? super V2, ? super V3> peek);
  <T extends Tuple> Nullable<T> selection(Function3<? super V1, ? super V2, ? super V3, ? extends T> flatMap);
  Tuple3<V1, V2, V3> where(Predicate3<? super V1, ? super V2, ? super V3> filter);
}

final class Triple<V1, V2, V3> implements Tuple3<V1, V2, V3> {
  private final V1 v1;
  private final V2 v2;
  private final V3 v3;

  @Contract(pure = true)
  Triple(V1 v1, V2 v2, V3 v3) {
    this.v1 = v1;
    this.v2 = v2;
    this.v3 = v3;
  }

  @Override
  public final <R> Nullable<R> select(Function3<? super V1, ? super V2, ? super V3, ? extends R> map) {
    return Nullable.of(nonNullable(map, "map").apply(v1, v2, v3));
  }

  @Override
  @Contract("_ -> this")
  public final Tuple3<V1, V2, V3> peek(Consumer3<? super V1, ? super V2, ? super V3> peek) {
    nonNullable(peek, "peek").accept(v1, v2, v3);
    return this;
  }

  @Override
  public final <T extends Tuple> Nullable<T> selection(Function3<? super V1, ? super V2, ? super V3, ? extends T> flatMap) {
    return Nullable.of(nonNullable(flatMap, "flatMap").apply(v1, v2, v3));
  }

  @Override
  public Tuple3<V1, V2, V3> where(Predicate3<? super V1, ? super V2, ? super V3> filter) {
    return nonNullable(filter, "filter").test(v1, v2, v3) ? this : new Empty3<>();
  }
}

final class Empty3<V1, V2, V3> implements Tuple3<V1, V2, V3> {
  @NotNull
  @Contract(pure = true)
  @Override
  public final  <R> Nullable<R> select(Function3<? super V1, ? super V2, ? super V3, ? extends R> map) {
    return Nullable.none();
  }

  @Contract(value = "_ -> this", pure = true)
  @Override
  public final Tuple3<V1, V2, V3> peek(Consumer3<? super V1, ? super V2, ? super V3> peek) {
    return this;
  }

  @Override
  public final  <T extends Tuple> Nullable<T> selection(Function3<? super V1, ? super V2, ? super V3, ? extends T> flatMap) {
    return Nullable.none();
  }

  @Override
  public final Tuple3<V1, V2, V3> where(Predicate3<? super V1, ? super V2, ? super V3> filter) {
    return new Empty3<>();
  }
}