package com.example.restauyou.ModelClass;

import android.widget.TextView;

public class MenuItem {

    private String itemTitle, itemDescription, itemAvailability, itemPrice ,itemImageUrl, itemId, itemCategory, itemImagePath;
    private int amount, itemImg;
    private boolean selected;

    public String getItemImagePath() {
        return itemImagePath;
    }

    public void setItemImagePath(String itemImagePath) {
        this.itemImagePath = itemImagePath;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }


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

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
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
    public MenuItem(String itemTitle, String itemDescription, String itemAvailability, String itemPrice,String itemImageUrl, String itemImagePath) {
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.itemAvailability = itemAvailability;
        this.itemPrice = itemPrice;
        this.itemImageUrl= itemImageUrl;
        this.itemImagePath = itemImagePath;
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
