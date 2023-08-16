package re.artoo.lance.query.one;

import org.junit.jupiter.api.Test;
import re.artoo.lance.query.One;
import re.artoo.lance.query.cursor.Fetch;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProjectableTest {
  @Test
  public void shouldFailWhenSelectThrowsException() {
    assertThrows(Fetch.Exception.class,
      () -> One.of(1).select(it -> {
        throw new IllegalStateException("Damn!");
      }).fetch(),
      "Damn!"
    );
  }

}
