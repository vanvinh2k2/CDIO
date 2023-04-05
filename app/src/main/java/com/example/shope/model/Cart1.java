package com.example.shope.model;

public class Cart1 {
    private String _id, userId;
    Product productId;
    Optioned optionStyle;
    int quantity;
    long price;
    //List<CartItem> cartItemIds;

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public Optioned getOptionStyle() {
        return optionStyle;
    }

    public void setOptionStyle(Optioned optionStyle) {
        this.optionStyle = optionStyle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
