package re.artoo.lance.query.cursor.operation;

import re.artoo.lance.func.TryIntFunction1;
import re.artoo.lance.query.Cursor;
import re.artoo.lance.query.FetchException;
import re.artoo.lance.query.cursor.Probe;

public record Map<ELEMENT, RETURN>(Probe<ELEMENT> probe, Reference<RETURN> reference, TryIntFunction1<? super ELEMENT, ? extends RETURN> operation) implements Cursor<RETURN> {
  public Map(Probe<ELEMENT> probe, TryIntFunction1<? super ELEMENT, ? extends RETURN> operation) {
    this(probe, Reference.iterative(), operation);
  }
  @Override
  public RETURN fetch() throws Throwable {
    return canFetch() ? reference.element() : FetchException.byThrowing("Can't fetch next element from cursor (no more mappable elements?)");
  }

  @Override
  public boolean canFetch() throws Throwable {
    if (reference.isNotFetched()) return true;
    if (!probe.canFetch()) return false;

    reference.element(operation.invoke(reference.indexPlusPlus(), probe.fetch()));

    return true;
  }
}
