package com.example.restauyou.ModelClass;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.restauyou.CartSharedPrefManager;

import java.util.ArrayList;

public class SharedCartModel extends ViewModel {

    private final MutableLiveData<ArrayList<CartItem>> cartList = new MutableLiveData<>(new ArrayList<>());

    public LiveData<ArrayList<CartItem>> getCartList() {
        return cartList;
    }

    public void addToCart(Context context, MenuItem item) {
        ArrayList<CartItem> current = cartList.getValue();

        // Check if item already exists
        assert current != null;
        for (CartItem cartItem: current) {
            if (cartItem.getMenuItem().getItemId().equals(item.getItemId())) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartList.setValue(current);
                saveCart(context, current);
                return;
            }
        }

        // If new item
        current.add(new CartItem(item,1));
        cartList.setValue(current);
        saveCart(context, current);
    }

    public void removeFromCart(Context context, MenuItem item) {
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
                saveCart(context, current);
                return;
            }
        }
        cartList.setValue(current);
        saveCart(context, current);
    }

    public void loadSharedPrefCart(Context context) {
        if((cartList.getValue() == null) || (cartList.getValue().isEmpty()))
            cartList.setValue(CartSharedPrefManager.loadCart(context));
    }

    public int getQuantity(MenuItem food) {
        ArrayList<CartItem> current = cartList.getValue();

        if (current == null)
            return 0;

        for (CartItem cartItem: current) {
            if (cartItem.getMenuItem().getItemId().equals(food.getItemId())) {
                return cartItem.getQuantity();
            }
        }
       return 0;
    }

    public void clearCart(Context context){
        cartList.getValue().clear();
        CartSharedPrefManager.saveCart(context, new ArrayList<>());
    }

    private void saveCart(Context context, ArrayList<CartItem> arr) {
        CartSharedPrefManager.saveCart(context, arr);
    }
}

