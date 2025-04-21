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
    private TableColumn<Order, String> clientNameColumn;
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
        orderIdColumn.setCellValueFactory(cell -> cell.getValue().orderIdProperty());
        clientNameColumn.setCellValueFactory(cell -> cell.getValue().clientNameProperty());
        totalPriceColumn.setCellValueFactory(cell -> cell.getValue().totalPriceProperty().asObject());
        statusColumn.setCellValueFactory(cell -> cell.getValue().statusProperty());

        ordersTable.setItems(ordersList);

        statusComboBox.getItems().addAll("Pending", "Shipped", "Delivered", "Canceled");

        addOrderBtn.setOnAction(e -> addOrder());
        removeOrderBtn.setOnAction(e -> removeSelectedOrder());
        clearTableBtn.setOnAction(e -> clearTable());

        loadOrdersFromDatabase(); // 🔁 Load on init
    }

    private void addOrder() {
        String orderId = orderIdField.getText().trim();
        String clientName = clientNameField.getText().trim();
        String priceText = totalPriceField.getText().trim();
        String status = statusComboBox.getValue();

        if (orderId.isEmpty() || clientName.isEmpty() || priceText.isEmpty() || status == null) {
            showAlert("Validation Error", "All fields must be filled!", Alert.AlertType.ERROR);
            return;
        }

        try {
            double totalPrice = Double.parseDouble(priceText);
            Order order = new Order(orderId, clientName, totalPrice, status);

            String sql = "INSERT INTO orders (order_id, client_name, total_price, status) VALUES (?, ?, ?, ?)";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, order.getOrderId());
                stmt.setString(2, order.getClientName());
                stmt.setDouble(3, order.getTotalPrice());
                stmt.setString(4, order.getStatus());
                stmt.executeUpdate();

                ordersList.add(order);
                clearForm();

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Database Error", "Could not insert order.", Alert.AlertType.ERROR);
            }

        } catch (NumberFormatException e) {
            showAlert("Format Error", "Total Price must be a number.", Alert.AlertType.ERROR);
        }
    }

    private void loadOrdersFromDatabase() {
        ordersList.clear();
        String sql = "SELECT * FROM orders";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String orderId = rs.getString("order_id");
                String clientName = rs.getString("client_name");
                double totalPrice = rs.getDouble("total_price");
                String status = rs.getString("status");

                ordersList.add(new Order(orderId, clientName, totalPrice, status));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void removeSelectedOrder() {
        Order selected = ordersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "No order selected!", Alert.AlertType.WARNING);
            return;
        }

        String sql = "DELETE FROM orders WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, selected.getOrderId());
            stmt.executeUpdate();
            ordersList.remove(selected);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Could not delete order.", Alert.AlertType.ERROR);
        }
    }

    private void clearTable() {
        String sql = "DELETE FROM orders";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            ordersList.clear();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        orderIdField.clear();
        clientNameField.clear();
        totalPriceField.clear();
        statusComboBox.setValue(null);
    }

    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
