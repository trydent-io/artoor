package re.artoo.lance.query.cursor;

import re.artoo.lance.func.TryIntPredicate1;
import re.artoo.lance.func.TryPredicate1;
import re.artoo.lance.query.Cursor;
import re.artoo.lance.query.cursor.operation.Filter;

public sealed interface Filterator<ELEMENT> extends Fetch<ELEMENT> permits Cursor {
  default Cursor<ELEMENT> filter(TryIntPredicate1<? super ELEMENT> filter) {
    return new Filter<>(this, filter);
  }
  default Cursor<ELEMENT> filter(TryPredicate1<? super ELEMENT> filter) {
    return filter((index, element) -> filter.invoke(element));
  }
  default Cursor<ELEMENT> nonNull() {
    return filter(Filter.presenceOnly());
  }
}
