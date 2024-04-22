module org.amalgam.lexilogicclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens org.amalgam.lexilogicclient to javafx.fxml;
    exports org.amalgam.lexilogicclient;
    exports org.amalgam.lexilogicclient.common;
    opens org.amalgam.lexilogicclient.common to javafx.fxml;
}