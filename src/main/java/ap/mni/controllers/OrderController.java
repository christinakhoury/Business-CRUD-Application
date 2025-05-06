package ap.mni.controllers;

import ap.mni.models.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ap.mni.controllers.DBConnection;

import java.sql.*;

public class OrderController {

    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, String> orderIdColumn;
    @FXML
    private TableColumn<Order, String> clientNameColumn;//erye5yw45b46h546
    @FXML
    private TableColumn<Order, Double> totalPriceColumn;
    @FXML
    private TableColumn<Order, String> statusColumn;

    @FXML
    private TextField orderIdField;
    @FXML
    private TextField clientNameField;
    @FXML
    private TextField totalPriceField;
    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private Button addOrderBtn;
    @FXML
    private Button removeOrderBtn;
    @FXML
    private Button clearTableBtn;

    private final ObservableList<Order> ordersList = FXCollections.observableArrayList();

   @FXML
public void initialize() {
    // Connect table columns to Order properties
    orderIdColumn.setCellValueFactory(cell -> cell.getValue().orderIdProperty());
    clientNameColumn.setCellValueFactory(cell -> cell.getValue().clientNameProperty());
    totalPriceColumn.setCellValueFactory(cell -> cell.getValue().totalPriceProperty().asObject());
    statusColumn.setCellValueFactory(cell -> cell.getValue().statusProperty());

    // Connect data model to table
    ordersTable.setItems(ordersList);

    // Setup status options
    statusComboBox.getItems().addAll("Pending", "Shipped", "Delivered", "Canceled");

    // Setup button actions
    addOrderBtn.setOnAction(e -> addOrder());
    removeOrderBtn.setOnAction(e -> removeSelectedOrder());
    clearTableBtn.setOnAction(e -> clearTable());

    // Load existing data
    loadOrdersFromDatabase(); // 🔁 Load on init
}

private void addOrder() {
    String orderId = orderIdField.getText().trim();  // Get order ID
    String clientName = clientNameField.getText().trim();  // Get client name
    String priceText = totalPriceField.getText().trim();  // Get price text
    String status = statusComboBox.getValue();  // Get order status

    if (orderId.isEmpty() || clientName.isEmpty() || priceText.isEmpty() || status == null) {  // Validate all fields
        showAlert("Validation Error", "All fields must be filled!", Alert.AlertType.ERROR);  // Show error message
        return;  // Stop execution
    }

    try {
        double totalPrice = Double.parseDouble(priceText);  // Convert to number
        Order order = new Order(orderId, clientName, totalPrice, status);  // Create order object

        String sql = "INSERT INTO orders (order_id, client_name, total_price, status) VALUES (?, ?, ?, ?)";  // Prepare SQL query

        try (Connection conn = DBConnection.getConnection();  // Get database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {  // Create prepared statement

            stmt.setString(1, order.getOrderId());  // Set order ID
            stmt.setString(2, order.getClientName());  // Set client name
            stmt.setDouble(3, order.getTotalPrice());  // Set total price
            stmt.setString(4, order.getStatus());  // Set order status
            stmt.executeUpdate();  // Execute database insert

            ordersList.add(order);  // Update UI table
            clearForm();  // Reset form fields

        } catch (SQLException e) {  // Handle SQL errors
            e.printStackTrace();  // Print error details
            showAlert("Database Error", "Could not insert order.", Alert.AlertType.ERROR);  // Show database error
        }

    } catch (NumberFormatException e) {  // Handle format errors
        showAlert("Format Error", "Total Price must be a number.", Alert.AlertType.ERROR);  // Show format error
    }
}

    private void loadOrdersFromDatabase() {
    ordersList.clear();  // Reset list
    String sql = "SELECT * FROM orders";  // Query all orders

    try (Connection conn = DBConnection.getConnection();  // Connect to database
         Statement stmt = conn.createStatement();  // Create SQL statement
         ResultSet rs = stmt.executeQuery(sql)) {  // Execute and retrieve

        while (rs.next()) {  // Process each row
            String orderId = rs.getString("order_id");  // Get order ID
            String clientName = rs.getString("client_name");  // Get client name
            double totalPrice = rs.getDouble("total_price");  // Get price value
            String status = rs.getString("status");  // Get order status

            ordersList.add(new Order(orderId, clientName, totalPrice, status));  // Update UI table
        }

    } catch (SQLException e) {  // Handle errors
        e.printStackTrace();  // Print error details
    }
}

private void removeSelectedOrder() {
    Order selected = ordersTable.getSelectionModel().getSelectedItem();  // Get selected order
    if (selected == null) {  // Check selection
        showAlert("Warning", "No order selected!", Alert.AlertType.WARNING);  // Show warning message
        return;  // Stop execution
    }

    String sql = "DELETE FROM orders WHERE order_id = ?";  // Prepare delete query

    try (Connection conn = DBConnection.getConnection();  // Connect to database
         PreparedStatement stmt = conn.prepareStatement(sql)) {  // Create prepared statement

        stmt.setString(1, selected.getOrderId());  // Set order ID
        stmt.executeUpdate();  // Execute deletion
        ordersList.remove(selected);  // Update UI table

    } catch (SQLException e) {  // Handle errors
        e.printStackTrace();  // Print error details
        showAlert("Error", "Could not delete order.", Alert.AlertType.ERROR);  // Show error message
    }
}

private void clearTable() {
    String sql = "DELETE FROM orders";  // Delete all query
    
    try (Connection conn = DBConnection.getConnection();  // Connect to database
         Statement stmt = conn.createStatement()) {  // Create SQL statement
         
        stmt.executeUpdate(sql);  // Execute deletion
        ordersList.clear();  // Clear UI table
        
    } catch (SQLException e) {  // Handle errors
        e.printStackTrace();  // Print error details
    }
}

private void clearForm() {
    orderIdField.clear();  // Reset ID field
    clientNameField.clear();  // Reset name field
    totalPriceField.clear();  // Reset price field
    statusComboBox.setValue(null);  // Reset status dropdown
}

private void showAlert(String title, String msg, Alert.AlertType type) {
    Alert alert = new Alert(type);  // Create alert dialog
    alert.setTitle(title);  // Set alert title
    alert.setHeaderText(null);  // Remove header
    alert.setContentText(msg);  // Set alert message
    alert.showAndWait();  // Display and wait
}
}
