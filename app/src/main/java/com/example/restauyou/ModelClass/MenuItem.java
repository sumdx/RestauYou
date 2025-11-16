package com.example.restauyou.ModelClass;

import android.widget.TextView;

public class MenuItem {

    String itemTitle;
    String itemDescription;
    String itemAvailability;
    String itemPrice ;
    String itemImageUrl;
    private String itemTitle, itemDescription,
            itemAvailability, // Acts as a boolean "Available" / "Not Available"
            itemPrice; // Acts as a double "$30.00"
    private int amount, itemImg;
    private boolean selected;

    public String getItemPrice() {
        return itemPrice;
    }


    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemAvailability() {
        return itemAvailability;
    }

    public void setItemAvailability(String itemAvailability) {
        this.itemAvailability = itemAvailability;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public int getItemImg() {
        return itemImg;
    }

    public void setItemImg(int itemImg) {
        this.itemImg = itemImg;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    // For admin side
    public MenuItem(String itemTitle, String itemDescription, String itemAvailability, String itemPrice) {
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.itemAvailability = itemAvailability;
        this.itemPrice = itemPrice;
        amount = 0;
        itemImg = 0;
        selected = false;
    }
    public MenuItem(String itemTitle, String itemDescription, String itemAvailability, String itemPrice,String itemImageUrl) {
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.itemAvailability = itemAvailability;
        this.itemPrice = itemPrice;
        this.itemImageUrl= itemImageUrl;
    }
    public MenuItem() {

    }



    // For customer side
    public MenuItem(String itemTitle, String itemDescription, String itemAvailability, String itemPrice, int amount, int itemImg, boolean selected) {
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.itemAvailability = itemAvailability;
        this.itemPrice = itemPrice;
        this.amount = amount;
        this.itemImg = itemImg;
        this.selected = selected;
    }
}
