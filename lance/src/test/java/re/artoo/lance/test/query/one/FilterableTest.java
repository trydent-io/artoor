package re.artoo.lance.test.query.one;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import re.artoo.lance.query.One;

import static org.assertj.core.api.Assertions.assertThat;

public class FilterableTest {
  @Test
  @DisplayName("fuffy should be vaxed")
  public void shouldBeVaxed() {
    final var fuffy = One.of(new re.artoo.lance.test.Test.Pet("Fuffy", true)).where(re.artoo.lance.test.Test.Pet::vaxed);

    assertThat(fuffy).isNotEmpty();
  }

  @Test
  public void shouldNotBeOlderThan5() {
    final var fuffy = One.of(new re.artoo.lance.test.Test.Pet("Fuffy", 7)).where(pet -> pet.age() <= 5);

    assertThat(fuffy).isEmpty();
  }

  @Test
  @DisplayName("fuffy should be a pet-type")
  public void shouldBeAPet() {
    final var fuffy = One.of((Record) new re.artoo.lance.test.Test.Pet("Fuffy", 5)).ofType(re.artoo.lance.test.Test.Pet.class);

    assertThat(fuffy.iterator().next()).isExactlyInstanceOf(re.artoo.lance.test.Test.Pet.class);
  }
}
