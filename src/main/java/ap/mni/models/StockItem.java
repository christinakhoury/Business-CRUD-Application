package ap.mni.models;

import javafx.beans.property.*;

public class StockItem {
    private final StringProperty id;
    private final StringProperty name;
    private final IntegerProperty quantity;
    private final IntegerProperty price;
    private final IntegerProperty finalPrice;

    public StockItem(String id, String name, int quantity, int price) {
        this(id, name, quantity, price, quantity * price);
    }

    public StockItem(String id, String name, int quantity, int price, int finalPrice) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleIntegerProperty(price);
        this.finalPrice = new SimpleIntegerProperty(finalPrice);
    }

    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public int getQuantity() { return quantity.get(); }
    public int getPrice() { return price.get(); }
    public int getFinalPrice() { return finalPrice.get(); }

    public StringProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public IntegerProperty quantityProperty() { return quantity; }
    public IntegerProperty priceProperty() { return price; }
    public IntegerProperty finalPriceProperty() { return finalPrice; }

    public void setQuantity(int quantity) { this.quantity.set(quantity); }
    public void setPrice(int price) { this.price.set(price); }
    public void setFinalPrice(int finalPrice) { this.finalPrice.set(finalPrice); }

}
