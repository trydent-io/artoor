package io.artoo.lance.value;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Int8(byte eval) implements Numeric<Int8> {
  public static final Int8 ZERO = new Int8((byte) 0);
  public static final Int8 ONE = new Int8((byte) 1);

  @Override
  public final @NotNull Byte raw() {
    return eval;
  }

  @Contract("_ -> new")
  @Override
  public <V extends Record & Numeric<V>> @NotNull Int8 add(@NotNull V value) {
    return new Int8((byte) (eval + value.raw().byteValue()));
  }

  @Contract("_ -> new")
  @Override
  public <V extends Record & Numeric<V>> @NotNull Int8 div(@NotNull V value) {
    return new Int8((byte) (eval / value.raw().byteValue()));
  }

  @Override
  @Contract(" -> new")
  public final @NotNull Int8 inc() {
    return new Int8((byte) (eval + 1));
  }
}

