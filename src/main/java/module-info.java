module com.groupe14ing2.gestioncongesabondants {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.groupe14ing2.gestioncongesabondants to javafx.fxml;
    exports com.groupe14ing2.gestioncongesabondants;
    exports com.groupe14ing2.gestioncongesabondants.controllers;
    opens com.groupe14ing2.gestioncongesabondants.controllers to javafx.fxml;
}