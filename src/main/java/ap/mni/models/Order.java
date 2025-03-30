package ap.mni.models;

import javafx.beans.property.*;

public class Order {
    private final StringProperty orderId;
    private final StringProperty clientName;
    private final DoubleProperty totalPrice;
    private final StringProperty status;

    public Order(String orderId, String clientName, double totalPrice, String status) {
        this.orderId = new SimpleStringProperty(orderId);
        this.clientName = new SimpleStringProperty(clientName);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
        this.status = new SimpleStringProperty(status);
    }

    // Getters & Setters
    public String getOrderId() { return orderId.get(); }
    public void setOrderId(String orderId) { this.orderId.set(orderId); }
    public StringProperty orderIdProperty() { return orderId; }

    public String getClientName() { return clientName.get(); }
    public void setClientName(String clientName) { this.clientName.set(clientName); }
    public StringProperty clientNameProperty() { return clientName; }

    public double getTotalPrice() { return totalPrice.get(); }
    public void setTotalPrice(double totalPrice) { this.totalPrice.set(totalPrice); }
    public DoubleProperty totalPriceProperty() { return totalPrice; }

    public String getStatus() { return status.get(); }
    public void setStatus(String status) { this.status.set(status); }
    public StringProperty statusProperty() { return status; }
}
