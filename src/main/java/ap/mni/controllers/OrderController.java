package ap.mni.controllers;

import ap.mni.models.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
        orderIdColumn.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
        clientNameColumn.setCellValueFactory(cellData -> cellData.getValue().clientNameProperty());
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        ordersTable.setItems(ordersList);


        statusComboBox.getItems().addAll("Pending", "Shipped", "Delivered", "Canceled");


        addOrderBtn.setOnAction(event -> addOrder());
        removeOrderBtn.setOnAction(event -> removeSelectedOrder());
        clearTableBtn.setOnAction(event -> clearTable());
    }

    private void addOrder() {
        String orderId = orderIdField.getText().trim();
        String clientName = clientNameField.getText().trim();
        String totalPriceText = totalPriceField.getText().trim();
        String status = statusComboBox.getValue();

        if (orderId.isEmpty() || clientName.isEmpty() || totalPriceText.isEmpty() || status == null) {
            showAlert("Error", "All fields must be filled!", Alert.AlertType.ERROR);
            return;
        }

        if (!orderId.matches("\\d+") || !totalPriceText.matches("\\d+(\\.\\d{1,2})?")) {
            showAlert("Error", "Order ID  and Total Price must be a valid number!", Alert.AlertType.ERROR);
            return;
        }

        double totalPrice = Double.parseDouble(totalPriceText);

        Order newOrder = new Order(orderId, clientName, totalPrice, status);
        ordersList.add(newOrder);
        orderIdField.clear();
        clientNameField.clear();
        totalPriceField.clear();
        statusComboBox.setValue(null);
    }

    private void removeSelectedOrder() {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            ordersList.remove(selectedOrder);
        } else {
            showAlert("Warning", "No order selected!", Alert.AlertType.WARNING);
        }
    }

    private void clearTable() {
        ordersList.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
