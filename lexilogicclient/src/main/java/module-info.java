module org.amalgam.lexilogicclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires rt;

    opens org.amalgam.lexilogicclient to javafx.fxml;
    exports org.amalgam.lexilogicclient;
}