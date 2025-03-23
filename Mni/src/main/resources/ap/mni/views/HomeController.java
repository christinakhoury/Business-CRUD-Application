package ap.mni.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
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
    private void openOrderManagement() {
        openNewWindow("/ap/mni/views/order-management.fxml", "Order Management");
    }


    @FXML
    private void openAccountManagement() {
        openNewWindow("/ap/mni/views/account-management.fxml", "Account Management");
    }


    @FXML
    private void openStockManagement() {
        openNewWindow("/ap/mni/views/stock-management.fxml", "Stock Management");
    }


    @FXML
    private void openCustomerManagement() {
        System.out.println("Customer Management button clicked!");
        openNewWindow("/ap/mni/views/clients-view.fxml", "Customer Management");
    }

    private void openNewWindow(String fxmlPath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
