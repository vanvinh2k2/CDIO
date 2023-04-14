package com.example.shope.utils;

import com.example.shope.model.Address;
import com.example.shope.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static String BASE_URL = "http://172.25.218.1:5000/api/v1/";
    public static String ADDRESS_URL = "https://vapi.vnappmob.com/api/";
    public static String YOUR_CLIENT_ID = "AQXz3r6p2IpMHDz7lC75I2WacClZG_cguCTxjcHW929X7s19GvVkcc6kgxupNXzR4LEi1LF19gLpi32G";
    public static List<Cart> allProduct = new ArrayList<>();
    public static List<Address> listAddress = new ArrayList<>();
    public static List<Cart> listProduct = new ArrayList<>();
    public static int CODE_PAYMENT = 0;
    public static int CODE_GOOGLE = 1000;
}
