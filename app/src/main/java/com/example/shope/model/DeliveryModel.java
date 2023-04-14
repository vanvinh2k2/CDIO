package com.example.shope.model;

import java.util.List;

public class DeliveryModel {
    boolean success;
    String message;
    List<Delivery> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Delivery> getData() {
        return data;
    }

    public void setData(List<Delivery> data) {
        this.data = data;
    }
}
