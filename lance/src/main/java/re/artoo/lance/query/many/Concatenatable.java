package re.artoo.lance.query.many;

import re.artoo.lance.Queryable;
import re.artoo.lance.query.Many;
import re.artoo.lance.query.cursor.routine.concat.Concat;

public interface Concatenatable<T> extends Queryable<T> {
  @SuppressWarnings("unchecked")
  default Many<T> concat(final T... elements) {
    return () -> cursor().to(Concat.array(elements));
  }

  default <Q extends Queryable<T>> Many<T> concat(final Q queryable) {
    return () -> cursor().to(Concat.liter(queryable.cursor()));
  }
}
