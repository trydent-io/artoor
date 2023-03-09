package re.artoo.lance.query.many;

import re.artoo.lance.Queryable;
import re.artoo.lance.func.TryPredicate1;
import re.artoo.lance.query.FetchException;
import re.artoo.lance.query.One;

import java.util.Optional;

public interface Uniquable<T> extends Queryable<T> {
  default One<T> at(final int at) {
    return () -> cursor().fold(null, (index, acc, element) -> at == index ? element : acc);
  }

  default One<T> first() {
    return first(it -> true);
  }

  default One<T> first(final TryPredicate1<? super T> where) {
    return () -> cursor()
      .filter(where)
      .reduce((index, first, element) -> first);
  }

  default One<T> last() {
    return last(it -> true);
  }

  default One<T> last(final TryPredicate1<? super T> where) {
    return () -> cursor().filter(where).reduce((last, element) -> element);
  }

  default One<T> single() {
    return single(it -> true);
  }

  default One<T> single(final TryPredicate1<? super T> where) {
    return () -> cursor()
      .filter(where)
      .reduce((index, single, element) -> FetchException.byThrowing("Can't fetch next single element, more than one single element has been found"));
  }
}

