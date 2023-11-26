module com.example.bomberscoobydoo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires javafx.media;

    opens com.example.bomberscoobydoo to javafx.fxml;
    exports com.example.bomberscoobydoo;
    exports com.example.bomberscoobydoo.control;
    opens com.example.bomberscoobydoo.control to javafx.fxml;
    exports com.example.bomberscoobydoo.screens;
    opens com.example.bomberscoobydoo.screens to javafx.fxml;
}