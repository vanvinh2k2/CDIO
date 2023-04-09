package com.example.shope.model;

import java.util.List;

public class AddressDefautlModel {
    private boolean success;
    private String message;
    private Address data;

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

    public Address getData() {
        return data;
    }

    public void setData(Address data) {
        this.data = data;
    }
}
