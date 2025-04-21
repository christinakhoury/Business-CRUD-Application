package ap.mni.controllers;

import ap.mni.models.SaleItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ap.mni.controllers.DBConnection;

import java.sql.*;

public class SalesAndOffersController {

    @FXML
    private TableView<SaleItem> salesTable;
    @FXML
    private TableColumn<SaleItem, String> productNameColumn;
    @FXML
    private TableColumn<SaleItem, Integer> quantityColumn;
    @FXML
    private TableColumn<SaleItem, Double> priceColumn;
    @FXML
    private TableColumn<SaleItem, Double> finalPriceColumn;

    @FXML
    private TextField productNameField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField priceField;

    @FXML
    private Button addSaleBtn;
    @FXML
    private Button removeSaleBtn;
    @FXML
    private Button clearTableBtn;

    private ObservableList<SaleItem> salesList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        finalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().finalPriceProperty().asObject());

        salesTable.setItems(salesList);

        addSaleBtn.setOnAction(event -> addSaleItem());
        removeSaleBtn.setOnAction(event -> removeSelectedSaleItem());
        clearTableBtn.setOnAction(event -> clearTable());

        loadSalesFromDatabase();
    }

    private void addSaleItem() {
        String productName = productNameField.getText().trim();
        String quantityText = quantityField.getText().trim();
        String priceText = priceField.getText().trim();

        if (productName.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
            showAlert("Error", "All fields must be filled!", Alert.AlertType.ERROR);
            return;
        }

        if (!quantityText.matches("\\d+") || !priceText.matches("\\d+(\\.\\d{1,2})?")) {
            showAlert("Error", "Quantity must be a whole number, and Price must be a valid number.", Alert.AlertType.ERROR);
            return;
        }

        int quantity = Integer.parseInt(quantityText);
        double price = Double.parseDouble(priceText);

        double discountRate = (quantity < 5) ? 0.2 : (quantity < 50) ? 0.4 : 0.6;
        double finalPrice = price * discountRate;

        SaleItem newSale = new SaleItem(productName, quantity, price, finalPrice);

        String sql = "INSERT INTO sales (product_name, quantity, price, final_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productName);
            stmt.setInt(2, quantity);
            stmt.setDouble(3, price);
            stmt.setDouble(4, finalPrice);
            stmt.executeUpdate();

            salesList.add(newSale);
            clearForm();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to insert into database.\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void removeSelectedSaleItem() {
        SaleItem selected = salesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "No item selected!", Alert.AlertType.WARNING);
            return;
        }

        String sql = "DELETE FROM sales WHERE product_name = ? AND quantity = ? AND price = ? LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, selected.getProductName());
            stmt.setInt(2, selected.getQuantity());
            stmt.setDouble(3, selected.getPrice());

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                salesList.remove(selected);
            } else {
                showAlert("Info", "Item not found in database.", Alert.AlertType.INFORMATION);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to delete from database.", Alert.AlertType.ERROR);
        }
    }

    private void clearTable() {
        String sql = "DELETE FROM sales";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            salesList.clear();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database error while clearing table.", Alert.AlertType.ERROR);
        }
    }

    private void loadSalesFromDatabase() {
        salesList.clear();
        String sql = "SELECT * FROM sales";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("product_name");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                double finalPrice = rs.getDouble("final_price");

                salesList.add(new SaleItem(name, quantity, price, finalPrice));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        productNameField.clear();
        quantityField.clear();
        priceField.clear();
    }

    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
