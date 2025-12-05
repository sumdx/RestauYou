package com.example.restauyou.ModelClass;

public class CartItem {
    private MenuItem menuItem;
    private int quantity;

    public CartItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice(){
<<<<<<< Updated upstream
        return (double) quantity*(Integer.parseInt(menuItem.getItemPrice()));
=======
        return (double) quantity*(Double.parseDouble(menuItem.getItemPrice()));
>>>>>>> Stashed changes

    }

    public CartItem(){

    }
}
