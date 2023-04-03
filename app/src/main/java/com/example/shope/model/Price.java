package com.example.shope.model;

import java.io.Serializable;

public class Price implements Serializable {
    long $numberDecimal;

    public long get$numberDecimal() {
        return $numberDecimal;
    }

    public void set$numberDecimal(long $numberDecimal) {
        this.$numberDecimal = $numberDecimal;
    }
}
