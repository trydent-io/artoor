package re.artoo.lance.query.many;

import re.artoo.lance.Queryable;
import re.artoo.lance.func.TryIntPredicate1;
import re.artoo.lance.func.TryPredicate1;
import re.artoo.lance.query.Cursor;
import re.artoo.lance.query.One;
import re.artoo.lance.query.closure.Count;

public interface Countable<T> extends Queryable<T> {
  default One<Integer> count() {
    return count(it -> true);
  }

  default One<Integer> count(TryIntPredicate1<? super T> where) {
    return () -> cursor().filter(where).reduce(0, (counted, element) -> element != null ? counted + 1 : counted);
  }
  default One<Integer> count(final TryPredicate1<? super T> where) {
    return count((index, it) -> where.invoke(it));
  }
}

