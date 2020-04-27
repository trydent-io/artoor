package trydent.desktop.event;

import trydent.desktop.property.LabeledProperty;
import trydent.func.$2.Cons;
import trydent.func.Func;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;

@FunctionalInterface
public interface LabeledEvent {
  static LabeledEvent mouseClicked(Func<MouseEvent, LabeledProperty> callback) {
    return it -> it.setOnMouseClicked(e -> callback.apply(e).onLabeled(it));
  }
  static LabeledEvent mouseClicked(Cons<MouseEvent, Labeled> callback) {
    return it -> it.setOnMouseClicked(e -> callback.accept(e, it));
  }

  void onLabeled(Labeled labeled);
}