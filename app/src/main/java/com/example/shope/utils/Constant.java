package com.example.shope.utils;

import com.example.shope.model.Address;
import com.example.shope.model.Cart;
import com.example.shope.model.Category;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static String BASE_URL = "http://192.168.244.58:5000/api/v1/";
    public static String ADDRESS_URL = "https://vapi.vnappmob.com/api/";
    public static String YOUR_CLIENT_ID = "AVm9fmANN9euH1b4XXzxQhch6SKZGMMd1vapJ_1B28V7wZuXltElTExDr1rtVUJkJ11lKOkpYae2sR05";
    public static List<Cart> allProduct = new ArrayList<>();
    public static List<Address> listAddress = new ArrayList<>();
    public static List<Category> listCategory = new ArrayList<>();
    public static List<Cart> listProduct = new ArrayList<>();
    public static int CODE_PAYMENT = 0;
    public static int CODE_GOOGLE = 1000;
}
