package com.example.shope.model;

import java.io.Serializable;

public class CartItem implements Serializable {
    String _id, cartId, storeId;
    int quantity;
    Optioned optionStyle;
    Product productId;

    public CartItem(String _id, String cartId) {
        this._id = _id;
        this.cartId = cartId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
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
