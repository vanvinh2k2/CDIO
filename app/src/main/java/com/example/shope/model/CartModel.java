package com.example.shope.model;

import java.util.List;

public class CartModel {
    String message;
    boolean success;
    List<Cart1> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Cart1> getData() {
        return data;
    }

    public void setData(List<Cart1> data) {
        this.data = data;
    }
}
