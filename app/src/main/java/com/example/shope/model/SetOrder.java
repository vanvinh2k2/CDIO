package com.example.shope.model;

public class SetOrder {
    String deliveryId, shippingAddressId, paymentMethod, productId, storeId;
    long shippingPrice, totalPrice;
    int quantity;
    Optioned optionStyle;

    public SetOrder(String storeId, String deliveryId, String shippingAddressId, String paymentMethod, String productId, long shippingPrice, long totalPrice, int quantity, Optioned optionStyle) {
        this.storeId = storeId;
        this.deliveryId = deliveryId;
        this.shippingAddressId = shippingAddressId;
        this.paymentMethod = paymentMethod;
        this.productId = productId;
        this.shippingPrice = shippingPrice;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.optionStyle = optionStyle;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Optioned getOptionStyle() {
        return optionStyle;
    }

    public void setOptionStyle(Optioned optionStyle) {
        this.optionStyle = optionStyle;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(String shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(long shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
