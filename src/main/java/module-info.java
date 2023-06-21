module com.project.movidle {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.project.movidle to javafx.fxml;
    exports com.project.movidle;
    exports com.project.movidle.View;
    opens com.project.movidle.View to javafx.fxml;
}