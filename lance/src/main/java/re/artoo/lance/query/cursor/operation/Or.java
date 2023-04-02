package re.artoo.lance.query.cursor.operation;

import re.artoo.lance.query.Cursor;
import re.artoo.lance.query.cursor.Fetch;
import re.artoo.lance.scope.Let;

public record Or<ELEMENT, PROBE extends Fetch<ELEMENT>>(Fetch<ELEMENT> probe, Let<PROBE> otherwise) implements Cursor<ELEMENT> {

  @Override
  public ELEMENT fetch() throws Throwable {
    return canFetch() ? probe.fetch() : null;
  }

  @Override
  public boolean canFetch() throws Throwable {
    boolean tickable = probe.canFetch();
    if (tickable) otherwise.flush();
    return tickable;
  }
}
