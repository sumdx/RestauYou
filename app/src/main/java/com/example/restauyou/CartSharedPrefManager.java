package com.example.restauyou;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.restauyou.ModelClass.CartItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Handles persistence logic for the cart using SharedPreferences and GSON.
 */
public class CartSharedPrefManager {
    private static final String PREFS_NAME = "CartPrefs";
    private static final String CART_KEY = "CurrentCart";
    private static final Gson gson = new Gson();

    public static ArrayList<CartItem> loadCart(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = sp.getString(CART_KEY, "");

        // No shared preference
        if (json.isEmpty())
            return new ArrayList<>();

        // Yes shared preference
        try {
            // Define arrayList type
            Type t = new TypeToken<ArrayList<CartItem>>() {}.getType();
            ArrayList<CartItem> list = gson.fromJson(json, t);   // json --> arrayList obj

            if (list == null) // If reading fails
                return new ArrayList<>();
            return list;
        }

        // If conversion fails
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void saveCart(Context context, ArrayList<CartItem> cart) {
        SharedPreferences.Editor e = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        e.putString(CART_KEY, gson.toJson(cart));
        e.apply();
    }
}
