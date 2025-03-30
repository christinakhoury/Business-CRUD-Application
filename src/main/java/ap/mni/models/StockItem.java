package ap.mni.models;

import javafx.beans.property.*;

public class StockItem {
    private final StringProperty id;
    private final StringProperty name;
    private final IntegerProperty quantity;
    private final IntegerProperty price;
    private final IntegerProperty finalPrice;

    public StockItem(String id, String name, int quantity, int price) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleIntegerProperty(price);
        this.finalPrice = new SimpleIntegerProperty(quantity * price);
    }

    public StockItem(String id, String name, int quantity, int price, int finalPrice) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleIntegerProperty(price);
        this.finalPrice = new SimpleIntegerProperty(finalPrice);
    }

    public StringProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public IntegerProperty quantityProperty() { return quantity; }
    public IntegerProperty priceProperty() { return price; }
    public IntegerProperty finalPriceProperty() { return finalPrice; }

    public int getQuantity() { return quantity.get(); }
    public int getPrice() { return price.get(); }
    public int getFinalPrice() { return finalPrice.get(); }
}
