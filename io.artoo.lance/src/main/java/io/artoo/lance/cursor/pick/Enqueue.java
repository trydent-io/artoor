package io.artoo.lance.cursor.pick;

import io.artoo.lance.cursor.Cursor;
import io.artoo.lance.cursor.Cursors;

import java.util.Arrays;

import static java.lang.System.arraycopy;

public final class Enqueue<T> implements Cursors<T> {
  private Cursor<T>[] elements;

  @SafeVarargs
  public Enqueue(final Cursor<T>... elements) {
    this.elements = elements;
  }

  @Override
  public boolean isEmpty() {
    return elements.length == 0;
  }

  @Override
  public Cursors<T> attach(final Cursor<T> element) {
    elements = Arrays.copyOf(elements, elements.length + 1);
    elements[elements.length - 1] = element;
    return this;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Cursor<T> detach() {
    if (isNotEmpty()) {
      final var element = elements[0];
      var items = new Cursor[elements.length - 1];
      arraycopy(elements, 1, items, 0, items.length);
      elements = (Cursor<T>[]) items;
      return element;
    }
    return null;
  }

  @Override
  public Cursor<T> peek() {
    return isNotEmpty() ? elements[0] : null;
  }
}