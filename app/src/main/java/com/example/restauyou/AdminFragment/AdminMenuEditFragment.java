package com.example.restauyou.AdminFragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.restauyou.AdminAdapters.AdminMenuEditAdapter;
import com.example.restauyou.AdminHomePageActivity;
import com.example.restauyou.ModelClass.MenuItem;
import com.example.restauyou.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminMenuEditFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    ArrayList<MenuItem> menuItemArraylist;
    AdminMenuEditAdapter adminMenuEditAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_menu_edit, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();

        menuItemArraylist = new ArrayList<>();


        recyclerView = view.findViewById(R.id.adminMenuItemRecyclerView);

        Button btnAddNewItem = view.findViewById(R.id.btnAddNewItem);
        adminMenuEditAdapter = new AdminMenuEditAdapter(getContext(),menuItemArraylist);
        recyclerView.setAdapter(adminMenuEditAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadMenuItems();
        btnAddNewItem.setOnClickListener(v -> {
            // Hide the default content (RecyclerView + Title)
            View contentLayout = getView().findViewById(R.id.editMenuContent);
            contentLayout.setVisibility(View.GONE);

            // Load AddNewItemFragment
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.menuEditContainer, new AdminMenuItemAddFragment())
                    .addToBackStack(null)
                    .commit();
        });


        return view;
    }

    private void loadMenuItems() {

        firebaseFirestore.collection("menu_items").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Log.d("FirebaseError", error.getMessage());
                    return;
                }
                if(value != null && !value.isEmpty()){
                    menuItemArraylist.clear();
                    for(DocumentSnapshot doc: value.getDocuments() ){
                        MenuItem item = doc.toObject(MenuItem.class);
                        menuItemArraylist.add(item);
                    }
                    adminMenuEditAdapter.setMenuItemArraylist(menuItemArraylist);
                }
            }
        });
    }


}