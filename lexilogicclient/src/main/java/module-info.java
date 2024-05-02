module org.amalgam.lexilogicclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires rt;

    opens org.amalgam.lexilogicclient to javafx.fxml;
    exports org.amalgam.lexilogicclient;
    opens org.amalgam.lexilogicclient.client.login to javafx.fxml;
    exports org.amalgam.lexilogicclient.client.login;
    opens org.amalgam.lexilogicclient.client.signup to javafx.fxml;
    exports org.amalgam.lexilogicclient.client.signup;
    opens org.amalgam.lexilogicclient.client.matchhistory to javafx.fxml;
    exports org.amalgam.lexilogicclient.client.matchhistory;
}