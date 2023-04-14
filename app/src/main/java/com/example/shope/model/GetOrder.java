package com.example.shope.model;

import java.io.Serializable;

public class GetOrder implements Serializable {
    String _id, createdAt;
    User userId;
    Store storeId;
    Delivery deliveryId;
    Address shippingAddressId;
    String status, paymentMethod;
    long shippingPrice, totalPrice;
    int quantity;
    Optioned optionStyle;
    Product productId;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Store getStoreId() {
        return storeId;
    }

    public void setStoreId(Store storeId) {
        this.storeId = storeId;
    }

    public Delivery getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Delivery deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Address getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Address shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    public Optioned getOptionStyle() {
        return optionStyle;
    }

    public void setOptionStyle(Optioned optionStyle) {
        this.optionStyle = optionStyle;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }
}
