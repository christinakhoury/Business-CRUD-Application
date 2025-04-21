package ap.mni.controllers;

import ap.mni.models.StockItem;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ap.mni.controllers.DBConnection;

import java.sql.*;

public class StockController {

    @FXML
    private TableView<StockItem> stockTable;
    @FXML
    private TableColumn<StockItem, String> productIdColumn;
    @FXML
    private TableColumn<StockItem, String> productNameColumn;
    @FXML
    private TableColumn<StockItem, Integer> quantityColumn;
    @FXML
    private TableColumn<StockItem, Integer> priceColumn;
    @FXML
    private TableColumn<StockItem, Integer> finalPriceColumn;

    @FXML
    private TextField productIdField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField quantityField;
    @FXML
    private Label priceLabel;
    @FXML
    private Slider priceSlider;

    @FXML
    private Button addStockBtn;
    @FXML
    private Button removeStockBtn;
    @FXML
    private Button clearTableBtn;

    private final ObservableList<StockItem> stockList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        productIdColumn.setCellValueFactory(data -> data.getValue().idProperty());
        productNameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        quantityColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());
        priceColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPrice()).asObject());
        finalPriceColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getFinalPrice()).asObject());

        stockTable.setItems(stockList);

        priceSlider.setMin(0);
        priceSlider.setMax(200);
        priceSlider.setValue(50);
        priceSlider.setShowTickLabels(true);
        priceSlider.setShowTickMarks(true);
        priceSlider.setMajorTickUnit(50);
        priceSlider.setBlockIncrement(1);

        priceSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int rounded = (int) Math.round(newVal.doubleValue());
            priceLabel.setText("Price: " + rounded);
        });

        addStockBtn.setOnAction(e -> addStockItem());
        removeStockBtn.setOnAction(e -> removeSelectedStockItem());
        clearTableBtn.setOnAction(e -> clearTable());

        loadStockFromDatabase(); // Load from DB on start
    }

    private void addStockItem() {
        String id = productIdField.getText().trim();
        String name = productNameField.getText().trim();
        String quantityText = quantityField.getText().trim();
        int price = (int) Math.round(priceSlider.getValue());

        if (id.isEmpty() || name.isEmpty() || quantityText.isEmpty()) {
            showAlert("Error", "All fields must be filled!", Alert.AlertType.ERROR);
            return;
        }

        if (!id.matches("\\d+") || !quantityText.matches("\\d+")) {
            showAlert("Error", "Product ID and Quantity must be whole numbers.", Alert.AlertType.ERROR);
            return;
        }

        int quantity = Integer.parseInt(quantityText);
        int finalPrice = quantity * price;

        StockItem newItem = new StockItem(id, name, quantity, price, finalPrice);

        String sql = "INSERT INTO stock (id, name, quantity, price, final_price) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setInt(3, quantity);
            stmt.setInt(4, price);
            stmt.setInt(5, finalPrice);

            stmt.executeUpdate();
            stockList.add(newItem);
            clearForm();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to insert into database.", Alert.AlertType.ERROR);
        }
    }

    private void loadStockFromDatabase() {
        stockList.clear();
        String sql = "SELECT * FROM stock";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
                int finalPrice = rs.getInt("final_price");

                stockList.add(new StockItem(id, name, quantity, price, finalPrice));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void removeSelectedStockItem() {
        StockItem selected = stockTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "No item selected!", Alert.AlertType.WARNING);
            return;
        }

        String sql = "DELETE FROM stock WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, selected.idProperty().get());
            stmt.executeUpdate();
            stockList.remove(selected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearTable() {
        String sql = "DELETE FROM stock";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            stockList.clear();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        productIdField.clear();
        productNameField.clear();
        quantityField.clear();
        priceSlider.setValue(50);
    }

    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
