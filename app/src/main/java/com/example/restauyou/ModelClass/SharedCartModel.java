package com.example.restauyou.ModelClass;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SharedCartModel extends ViewModel {

    private final MutableLiveData<ArrayList<CartItem>> cartList = new MutableLiveData<>(new ArrayList<>());

    public LiveData<ArrayList<CartItem>> getCartList() {
        return cartList;
    }

    public void addToCart(MenuItem item) {
        ArrayList<CartItem> current = cartList.getValue();

        // Check if item already exists
        assert current != null;
        for (CartItem cartItem : current) {
            if (cartItem.getMenuItem().getItemId().equals(item.getItemId())) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartList.setValue(current);
                return;
            }
        }

        // If new item
        current.add(new CartItem(item,1));
        cartList.setValue(current);
    }

    public void removeFromCart(MenuItem item) {
        ArrayList<CartItem> current = cartList.getValue();

        assert current != null;

        for (CartItem cartItem : current) {
            if (cartItem.getMenuItem().getItemId().equals(item.getItemId())) {
                if(cartItem.getQuantity()>1){
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                }else{
                    current.remove(cartItem);
                }
                cartList.setValue(current);
                return;
            }
        }
        cartList.setValue(current);
    }

    public int getQuantity(MenuItem food) {
        ArrayList<CartItem> current = cartList.getValue();
        for (CartItem cartItem: current) {
            if (cartItem.getMenuItem().getItemId().equals(food.getItemId())) {
                return cartItem.getQuantity();
            }
        }
       return 0;
    }
}

