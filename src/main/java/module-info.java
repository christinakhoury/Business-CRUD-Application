module ap.mni {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ap.mni.controllers to javafx.fxml;
    opens ap.mni to javafx.fxml;
    exports ap.mni;
}
