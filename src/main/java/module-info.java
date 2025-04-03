module com.example.farmacia0304 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    //requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.farmacia0304.model to javafx.base;
    opens com.example.farmacia0304 to javafx.fxml;
    exports com.example.farmacia0304;
    exports com.example.farmacia0304.controller;
    opens com.example.farmacia0304.controller to javafx.fxml;
}