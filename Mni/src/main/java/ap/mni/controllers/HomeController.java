package ap.mni.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeController {

    @FXML
    private Button orderManagment;

    @FXML
    private Button accountManagment;

    @FXML
    private Button stockManagment;

    @FXML
    private Button customerM;

    @FXML
    void openPersonsMng(ActionEvent event) throws IOException {
        openWindow("/ap/mni/views/persons-view.fxml", "Order Management");
    }

    @FXML
    void accountManagment(ActionEvent event) throws IOException {
        openWindow("/ap/mni/views/account-view.fxml", "Account Management");
    }

    @FXML
    void stockManagment(ActionEvent event) throws IOException {
        openWindow("/ap/mni/views/stock-view.fxml", "Stock Management");
    }

    @FXML
    void customerM(ActionEvent event) throws IOException {
        openWindow("/ap/mni/views/clients-view.fxml", "Customer Management");
    }

    /**
     * Utility method to open a new window.
     * @param fxmlPath Path to the FXML file.
     * @param title Window title.
     */
    private void openWindow(String fxmlPath, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
