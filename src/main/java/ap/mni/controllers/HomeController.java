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
    private Button stockManagment;

    @FXML
    private Button customerM;

    @FXML
    private Button salesAndOffering;

    @FXML
    void openOrderManagement(ActionEvent event) throws IOException {
        openWindow("/ap/mni/views/OrderView.fxml", "Order Management");
    }

    @FXML
    void openStockManagement(ActionEvent event) throws IOException {
        openWindow("/ap/mni/views/stock-view.fxml", "Stock Management");
    }

    @FXML
    void customerM(ActionEvent event) throws IOException {
        openWindow("/ap/mni/views/clients-view.fxml", "Customer Management");
    }

    @FXML
    void openSalesAndOffersManagement(ActionEvent event) throws IOException {
        openWindow("/ap/mni/views/Sale-view.fxml", "Sales and Offering");
    }

    private void openWindow(String fxmlPath, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.show();
    }
}
