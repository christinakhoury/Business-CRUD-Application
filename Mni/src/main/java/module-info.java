module ap.mni {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    opens ap.mni.controllers to javafx.fxml;
    opens ap.mni to javafx.fxml;
    exports ap.mni;
}