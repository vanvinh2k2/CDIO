package com.example.shope.model;

import java.io.Serializable;
import java.util.List;

public class Option implements Serializable {
    String _id, name;
    List<OptionItem> optionStylesItem;

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

    public List<OptionItem> getOptionStylesItem() {
        return optionStylesItem;
    }

    public void setOptionStylesItem(List<OptionItem> optionStylesItem) {
        this.optionStylesItem = optionStylesItem;
    }
}
