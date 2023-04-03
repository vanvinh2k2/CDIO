package com.example.shope.model;

import java.util.List;

public class Cart {
    private String _id, userId;
    List<CartItem> cartItemIds;

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

    public List<CartItem> getCartItemIds() {
        return cartItemIds;
    }

    public void setCartItemIds(List<CartItem> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }
}
