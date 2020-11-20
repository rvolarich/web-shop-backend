package com.volare_automation.springwebshop.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item {

    private int product_id;
    private String product_name;
    private String product_description;
    private int product_quantity;
    private double product_price;
    String product_image;

    public Item() {
    }



    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }



}
