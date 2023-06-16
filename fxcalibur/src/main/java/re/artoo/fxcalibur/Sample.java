package re.artoo.fxcalibur;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import re.artoo.fxcalibur.element.component.button;
import re.artoo.fxcalibur.element.component.button.color;
import re.artoo.fxcalibur.element.component.button.emphasis;
import re.artoo.fxcalibur.element.component.button.behaviour;
import re.artoo.fxcalibur.element.component.button.type;

import static re.artoo.fxcalibur.element.component.button.Default;
import static re.artoo.fxcalibur.element.component.button.value;

public class Sample extends Application {
  private static final Asset buttonCss = Asset.css("button");
  private static final Asset mim = Asset.css("mim");

  public static void main(String[] args) {
    launch(Sample.class);
  }

  @Override
  public void start(Stage stage) throws Exception {
    //setUserAgentStylesheet(Dragon.Blue.getUserAgentStylesheet());
    Font.loadFont(Asset.font("DMSans-Medium.tff").load(), 14);
    VBox box = new VBox(16,
      new Default(emphasis.primary, behaviour.basic, color.emerald, value.text("Emerald")),
      new Default(emphasis.negative, behaviour.submit, color.yellow, value.text("Yellow")),
      new Default(emphasis.primary, type.tertiary, value.text("Orange")),
      new Default(behaviour.submit, color.teal, value.text("Teal")),
      new Default(behaviour.submit, color.violet, value.text("Violet")),
      new Default(behaviour.submit, color.purple, value.text("Purple")),
      new Default(behaviour.submit, color.gradient, value.text("Gradient"))
    );
    box.setPadding(new Insets(16));
    var scene = new Scene(box, 800, 600);
    //scene.getStylesheets().add(buttonCss.location().toExternalForm());
    scene.getStylesheets().add(mim.location().toExternalForm());
    box.setBackground(Background.fill(Color.TRANSPARENT));
    scene.setFill(Color.TRANSPARENT);
    stage.initStyle(StageStyle.TRANSPARENT);
    stage.setScene(scene);
    stage.show();
  }
}
