package com.volare_automation.springwebshop.model;

public class CartItems {

    private String name;
    private int quantity;
    private int price;
    private int id;

    public CartItems() {
    }

    public CartItems(String name, int quantity, int price, int id) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
