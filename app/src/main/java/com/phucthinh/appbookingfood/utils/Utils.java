package com.phucthinh.appbookingfood.utils;

import com.phucthinh.appbookingfood.model.Cart;
import com.phucthinh.appbookingfood.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String BASE_URL = "http://192.168.1.2/appbookingfood/";
    public static List<Cart> arrCart;
    public static List<Cart> arrCartPay = new ArrayList<>();
    public static User user_current = new User();
}