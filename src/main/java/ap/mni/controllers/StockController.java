package ap.mni.controllers;

import ap.mni.models.StockItem;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

        productIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        priceColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrice()).asObject());
        finalPriceColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getFinalPrice()).asObject()); // New binding

        priceSlider.setMin(0);
        priceSlider.setMax(200);
        priceSlider.setValue(50); // Default value
        priceSlider.setShowTickLabels(true);
        priceSlider.setShowTickMarks(true);
        priceSlider.setMajorTickUnit(50);
        priceSlider.setBlockIncrement(1);


        priceSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int roundedPrice = (int) Math.round(newVal.doubleValue());
            priceLabel.setText("Price: " + roundedPrice);
        });


        stockTable.setItems(stockList);


        addStockBtn.setOnAction(event -> addStockItem());
        removeStockBtn.setOnAction(event -> removeSelectedStockItem());
        clearTableBtn.setOnAction(event -> clearTable());
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
            showAlert("Error", "Invalid input! Product ID and Quantity must be whole numbers.", Alert.AlertType.ERROR);
            return;
        }

        int quantity = Integer.parseInt(quantityText);
        int finalPrice = quantity * price;

        StockItem newStockItem = new StockItem(id, name, quantity, price, finalPrice);
        stockList.add(newStockItem);

        productIdField.clear();
        productNameField.clear();
        quantityField.clear();
        priceSlider.setValue(50);
    }

    private void removeSelectedStockItem() {
        StockItem selectedStockItem = stockTable.getSelectionModel().getSelectedItem();
        if (selectedStockItem != null) {
            stockList.remove(selectedStockItem);
        } else {
            showAlert("Warning", "No item selected!", Alert.AlertType.WARNING);
        }
    }

    private void clearTable() {
        stockList.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
