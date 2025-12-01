package com.example.restauyou.ModelClass;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private ArrayList<CartItem> cartItems;
    private String orderId;
    private String userId;
    private String tableNo;
    private String orderStatus; // pending, preparing, ready, served
    private double totalPrice;
    private Date createdAt;
    private Date updatedAt;

    public Order(ArrayList<CartItem> cartItems, String orderId, String userId, String tableNo, String orderStatus, double totalPrice, Date createdAt, Date updatedAt) {
        this.cartItems = cartItems;
        this.orderId = orderId;
        this.userId = userId;
        this.tableNo = tableNo;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Order() {}

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
