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
    private Button orderManagment;  // Order button reference
    
    @FXML
    private Button stockManagment;  // Stock button reference
    
    @FXML
    private Button customerM;  // Customer button reference
    
    @FXML
    private Button salesAndOffering;  // Sales button reference
    
    @FXML
    void openOrderManagement(ActionEvent event) throws IOException {  // Handle order click
        openWindow("/ap/mni/views/OrderView.fxml", "Order Management");  // Open orders window
    }
    
    @FXML
    void openStockManagement(ActionEvent event) throws IOException {  // Handle stock click
        openWindow("/ap/mni/views/stock-view.fxml", "Stock Management");  // Open stock window
    }
    
    @FXML
    void customerM(ActionEvent event) throws IOException {  // Handle customer click
        openWindow("/ap/mni/views/clients-view.fxml", "Customer Management");  // Open customers window
    }
    
    @FXML
    void openSalesAndOffersManagement(ActionEvent event) throws IOException {  // Handle sales click
        openWindow("/ap/mni/views/Sale-view.fxml", "Sales and Offering");  // Open sales window
    }
    
    private void openWindow(String fxmlPath, String title) throws IOException {  // Handle window creation
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));  // Load view file
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);  // Create display scene
        Stage stage = new Stage();  // Create new window
        stage.setTitle(title);  // Set window title
        stage.setScene(scene);  // Set window content
        stage.setResizable(false);  // Disable resizing
        stage.setMaximized(false);  // Prevent maximizing
        stage.show();  // Display window
    }
}