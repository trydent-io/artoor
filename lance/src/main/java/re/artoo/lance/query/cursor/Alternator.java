package re.artoo.lance.query.cursor;

import re.artoo.lance.func.TryConsumer1;
import re.artoo.lance.func.TryFunction1;
import re.artoo.lance.func.TryFunction2;
import re.artoo.lance.func.TryIntConsumer1;
import re.artoo.lance.query.Cursor;
import re.artoo.lance.query.cursor.operation.*;

public sealed interface Alternator<ELEMENT> extends Fetch<ELEMENT> permits Cursor {
  default <FETCH extends Fetch<ELEMENT>> Cursor<ELEMENT> or(FETCH otherwise) {
    return new Or<>(this, otherwise);
  }


  default <E extends RuntimeException> Cursor<ELEMENT> or(final String message, final TryFunction2<? super String, ? super Throwable, ? extends E> exception) {
    return null;
  }

  default Cursor<ELEMENT> or(final String message, final TryFunction1<? super String, ? extends Throwable> exception) {
    return new Er<>(this, message, exception);
  }

  default Cursor<ELEMENT> exceptionally(TryConsumer1<? super Throwable> catch$) {
    return new Catch<>(this, (__, throwable) -> catch$.invoke(throwable));
  }

  default Cursor<ELEMENT> exceptionally(TryFunction1<? super Throwable, ? extends Throwable> rethrow) {
    return new Rethrow<>(this, (__, throwable) -> rethrow.invoke(throwable));
  }

  interface TryFunc1<T, R> extends TryFunction1<T, R> {}

  default Cursor<ELEMENT> exceptionally(TryFunc1<? super Throwable, ? extends ELEMENT> recover) {
    return new Recover<>(this, (__, throwable) -> recover.invoke(throwable));
  }

  default Cursor<ELEMENT> exceptionally(TryIntConsumer1<? super Throwable> catch$) {
    return new Catch<>(this, catch$);
  }
}

