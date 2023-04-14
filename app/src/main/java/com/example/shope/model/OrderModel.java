package com.example.shope.model;

import java.util.List;

public class OrderModel {
    boolean success;
    String message;
    List<GetOrder> data;

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

    public List<GetOrder> getData() {
        return data;
    }

    public void setData(List<GetOrder> data) {
        this.data = data;
    }
}
