package ap.mni.controllers;

import ap.mni.models.StockItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
/**
 * Controller class for managing stock items.
 * Handles UI interactions, database operations (CRUD), and updates the TableView accordingly.
 */
public class StockController {

    @FXML private TableView<StockItem> stockTable;
    @FXML private TableColumn<StockItem, String> productIdColumn;
    @FXML private TableColumn<StockItem, String> productNameColumn;
    @FXML private TableColumn<StockItem, Integer> quantityColumn;
    @FXML private TableColumn<StockItem, Integer> priceColumn;
    @FXML private TableColumn<StockItem, Integer> finalPriceColumn;

    @FXML private TextField productIdField;
    @FXML private TextField productNameField;
    @FXML private TextField quantityField;
    @FXML private Label priceLabel;
    @FXML private Slider priceSlider;

    @FXML private Button addStockBtn;
    @FXML private Button removeStockBtn;
    @FXML private Button clearTableBtn;
    /**
     * Observable list that backs the TableView.
     * Updates the UI automatically when modified.
     */
    private final ObservableList<StockItem> stockList = FXCollections.observableArrayList();
/*Creates a dynamic list that updates the TableView when modified. */
    @FXML
    public void initialize() {
        productIdColumn.setCellValueFactory(data -> data.getValue().idProperty());
        productNameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        quantityColumn.setCellValueFactory(data -> data.getValue().quantityProperty().asObject());
        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty().asObject());
        finalPriceColumn.setCellValueFactory(data -> data.getValue().finalPriceProperty().asObject());

        stockTable.setItems(stockList); //Connects stockList to TableView
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

        loadStockFromDatabase(); //loadStockFromDatabase();

    }
    /**
     * Adds a new StockItem based on input fields, saves it to the database, and updates the TableView.
     */

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

        String sql = "INSERT INTO stock (id, name, quantity, price, final_price) VALUES (?, ?, ?, ?, ?)"; //Insert into DB:
//The ? are placeholders (parameters) for the actual values — to prevent SQL injection and make it safer
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) { //) prepares the SQL statement for execution.

            stmt.setString(1, newItem.getId());
            stmt.setString(2, newItem.getName());
            stmt.setInt(3, newItem.getQuantity());
            stmt.setInt(4, newItem.getPrice());
            stmt.setInt(5, newItem.getFinalPrice());
//hole fill in order badel ?
            stmt.executeUpdate();
            stockList.add(newItem);
            clearForm();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to insert into database.", Alert.AlertType.ERROR);
        }
    }
    /**
     * Loads all stock records from the database into the observable list and displays them in the TableView.
     */
    private void loadStockFromDatabase() {//Clears current list and reloads all stock data from the stock table
        stockList.clear();
        String sql = "SELECT * FROM stock"; //loads all rows from the stock table.

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {//rs.next() loops over rows ,we extract data using rs.get<Type>()
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
            //Deletes from database and removes the item from the TableView list.

            stmt.setString(1, selected.getId());
            stmt.executeUpdate();
            stockList.remove(selected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Clears all records from the database table and the TableView.
     */

    private void clearTable() {
        String sql = "DELETE FROM stock"; //delete kl chi

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            stockList.clear();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//after adding an item restore eve and let the priceslider back to its default value
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
