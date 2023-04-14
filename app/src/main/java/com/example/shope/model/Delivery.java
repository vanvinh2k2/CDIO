package com.example.shope.model;

import java.io.Serializable;

public class Delivery implements Serializable {
    Price price;
    boolean isDefaultDelivery;
    String _id, name, description, logo;

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public boolean getDefaultDelivery() {
        return isDefaultDelivery;
    }

    public void setDefaultDelivery(boolean defaultDelivery) {
        isDefaultDelivery = defaultDelivery;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
