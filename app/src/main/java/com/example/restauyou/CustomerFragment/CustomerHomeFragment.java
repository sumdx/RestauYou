package com.example.restauyou.CustomerFragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.restauyou.CustomerHomePageActivity;
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

public class CustomerHomeFragment extends Fragment {
    RecyclerView chooseRv, displayRv;
    ImageButton cartBtn;
    FirebaseFirestore firebaseFirestore;
    ArrayList<MenuItem> foods = new ArrayList<>();
    MenuAdapter menuAdapter;
    SharedCartModel sharedCartItemsList ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cusomter_home, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        sharedCartItemsList = new ViewModelProvider(requireActivity()).get(SharedCartModel.class);
        // Initial objects by ids
        chooseRv = view.findViewById(R.id.chooseRecyclerView);
        displayRv = view.findViewById(R.id.displayRecyclerView);
        cartBtn = view.findViewById(R.id.cartBtn);

        // Test values for now
        ArrayList<MenuFilter> choose = new ArrayList<>();
        choose.add(new MenuFilter("All", true));
        choose.add(new MenuFilter("Burgers", false));
        choose.add(new MenuFilter("Pizza", false));
        choose.add(new MenuFilter("Pasta", false));
        choose.add(new MenuFilter("Sushi", false));



        // Set adapter
        menuAdapter = new MenuAdapter(getContext(), foods,sharedCartItemsList);
        displayRv.setAdapter(menuAdapter);
        chooseRv.setAdapter(new MenuFilterAdapter(getContext(), choose));
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

        // Cart listener (Using main thread)
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View home = requireActivity().getWindow().getDecorView().getRootView();
                home.post(new Runnable() {
                    @Override
                    public void run() {
                        if (getActivity() instanceof CustomerHomePageActivity)
                            ((CustomerHomePageActivity) getActivity()).cartBtnClicked();
                    }
                });
            }
        });

        return view;
    }

    private void loadMenuItem() {
        firebaseFirestore.collection("menu_items").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Log.d("FirebaseError", error.getMessage());
                    return;
                }
                if(value != null && !value.isEmpty()){
                    foods.clear();
                    for(DocumentSnapshot doc: value.getDocuments() ){
                        MenuItem item = doc.toObject(MenuItem.class);
                        item.setItemId(doc.getId());
                        foods.add(item);

                    }
                    menuAdapter.setFoods(foods);
                }
            }
        });
    }
}