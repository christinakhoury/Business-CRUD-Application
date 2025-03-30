package ap.mni.models;

import javafx.beans.property.*;

public class SaleItem {
    private final StringProperty productName;
    private final IntegerProperty quantity;
    private final DoubleProperty price;
    private final DoubleProperty finalPrice;

    public SaleItem(String productName, int quantity, double price, double finalPrice) {
        this.productName = new SimpleStringProperty(productName);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.finalPrice = new SimpleDoubleProperty(finalPrice);
    }


    public StringProperty productNameProperty() {
        return productName;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public DoubleProperty finalPriceProperty() {
        return finalPrice;
    }


    public String getProductName() {
        return productName.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public double getPrice() {
        return price.get();
    }

    public double getFinalPrice() {
        return finalPrice.get();
    }


    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice.set(finalPrice);
    }
}
