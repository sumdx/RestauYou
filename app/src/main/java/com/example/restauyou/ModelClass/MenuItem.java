package com.example.restauyou.ModelClass;

import android.widget.TextView;

public class MenuItem {

    String itemTitle;
    String itemDescription;
    String itemAvailability;
    String itemPrice ;
    String itemImageUrl;

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



    public MenuItem(String itemTitle, String itemDescription, String itemAvailability, String itemPrice) {
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.itemAvailability = itemAvailability;
        this.itemPrice = itemPrice;
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






}
