module io.arto.ddd {
  requires io.artoo.lance;

  requires org.jetbrains.annotations;
  requires io.vertx.core;
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;

  opens io.artoo.ddd.aggregate to com.fasterxml.jackson.databind;

}
