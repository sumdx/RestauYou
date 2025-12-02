package com.example.restauyou.CustomerFragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.restauyou.CustomerAdapters.MenuFilterAdapter;
import com.example.restauyou.CustomerAdapters.MenuAdapter;
import com.example.restauyou.ModelClass.CartItem;
import com.example.restauyou.ModelClass.MenuFilter;
import com.example.restauyou.ModelClass.MenuItem;
import com.example.restauyou.ModelClass.SharedCartModel;
import com.example.restauyou.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class CustomerHomeFragment extends Fragment {
    RecyclerView chooseRv, displayRv;
    ImageButton cartBtn;
    FirebaseFirestore firebaseFirestore;
    ArrayList<MenuItem> foods = new ArrayList<>();
    MenuAdapter menuAdapter;
    SharedCartModel sharedCartItemsList ;
    private View contentLayout;
    private FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cusomter_home, container, false);

        // Load sharedCartItems List (if present)
        firebaseFirestore = FirebaseFirestore.getInstance();
        sharedCartItemsList = new ViewModelProvider(requireActivity()).get(SharedCartModel.class);
        sharedCartItemsList.loadSharedPrefCart(getContext());

        // Initialize objects by ids
        chooseRv = view.findViewById(R.id.chooseRecyclerView);
        displayRv = view.findViewById(R.id.displayRecyclerView);
        cartBtn = view.findViewById(R.id.cartBtn);

        // Initialize manager
        fm = getParentFragmentManager();

//        // Test values for now
//        ArrayList<MenuFilter> choose = new ArrayList<>();
//        choose.add(new MenuFilter("All", true));
//        choose.add(new MenuFilter("Burgers", false));
//        choose.add(new MenuFilter("Pizza", false));
//        choose.add(new MenuFilter("Pasta", false));
//        choose.add(new MenuFilter("Sushi", false));



        // Set adapters
        menuAdapter = new MenuAdapter(getContext(), foods, sharedCartItemsList);
        displayRv.setAdapter(menuAdapter);
//        chooseRv.setAdapter(new MenuFilterAdapter(getContext(), choose));

        // When cart changes, update menu UI
        sharedCartItemsList.getCartList().observe(getViewLifecycleOwner(), new Observer<ArrayList<CartItem>>() {
            @Override
            public void onChanged(ArrayList<CartItem> cartItems) {
                menuAdapter.notifyDataSetChanged();
            }
        });

        // Set layout
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        chooseRv.setLayoutManager(llm);

        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        displayRv.setLayoutManager(llm);

        // Load items
        loadMenuItem();

        // Cart listener
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the default content (RecyclerView + Title)
                assert getView() != null;
                contentLayout = getView().findViewById(R.id.homeMenuContent);
                contentLayout.setVisibility(View.GONE);

                // Load CustomerCartFragment
                fm.beginTransaction()
                    .replace(R.id.homeEditContainer, new CustomerCartFragment())
                    .addToBackStack(null)
                    .commit();
            }
        });

        // Handle device's back button
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // Checks if the Home Fragment is resumed
                if (isResumed()) {
                    if (contentLayout != null)
                        contentLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    // Load items from Firebase
    private void loadMenuItem() {
        firebaseFirestore.collection("menu_items").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Log.d("FirebaseError", Objects.requireNonNull(error.getMessage()));
                    return;
                }

                if(value != null && !value.isEmpty()){
                    foods.clear();
                    for(DocumentSnapshot doc: value.getDocuments() ){
                        MenuItem item = doc.toObject(MenuItem.class);
                        assert item != null;
                        item.setItemId(doc.getId());

                        // Check if already in cart (from shared preference)
                        int cartedAmount = sharedCartItemsList.getQuantity(item);
                        item.setAmount(cartedAmount);
                        item.setSelected(cartedAmount > 0);

                        foods.add(item);
                    }


                    menuAdapter.setFoods(foods);
                }
            }
        });
    }
}