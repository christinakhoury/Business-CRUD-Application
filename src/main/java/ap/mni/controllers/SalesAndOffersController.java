package ap.mni.controllers;

import ap.mni.models.SaleItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

        SaleItem newSaleItem = new SaleItem(productName, quantity, price, finalPrice);
        salesList.add(newSaleItem);


        productNameField.clear();
        quantityField.clear();
        priceField.clear();
    }

    private void removeSelectedSaleItem() {
        SaleItem selectedSaleItem = salesTable.getSelectionModel().getSelectedItem();
        if (selectedSaleItem != null) {
            salesList.remove(selectedSaleItem);
        } else {
            showAlert("Warningg", "No item selected!", Alert.AlertType.WARNING);
        }
    }

    private void clearTable() {
        salesList.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
