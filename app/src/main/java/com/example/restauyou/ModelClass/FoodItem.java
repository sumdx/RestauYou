package com.example.restauyou.ModelClass;

public class FoodItem {

    private String title, description, amount, price;
    private int img;

    public FoodItem(int i, String t, String d, String a, String p) {
        img = i;
        title = t;
        description = d;
        amount = a;
        price = p;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String t) {
        title = t;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String d) {
        description = d;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String a) {
        amount = a;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String p) {
        price = p;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int i) {
        img = i;
    }
}
