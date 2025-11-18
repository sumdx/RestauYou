package com.example.restauyou.AdminFragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.restauyou.AdminAdapters.AdminMenuEditAdapter;
import com.example.restauyou.ModelClass.MenuItem;
import com.example.restauyou.R;

import java.util.ArrayList;

public class AdminMenuEditFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<MenuItem> menuItemArraylist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_menu_edit, container, false);
        menuItemArraylist = new ArrayList<>();
        menuItemArraylist.add(new MenuItem("Burger","hjfbsjhHBFJHSBJHFB","avaialble","$30.0"));
        menuItemArraylist.add(new MenuItem("Burger","hjfbsjhHBFJHSBJHFB","avaialble","$30.0"));
        menuItemArraylist.add(new MenuItem("Burger","hjfbsjhHBFJHSBJHFB","avaialble","$30.0"));
        menuItemArraylist.add(new MenuItem("Burger","hjfbsjhHBFJHSBJHFB","avaialble","$30.0"));
        menuItemArraylist.add(new MenuItem("Burger","hjfbsjhHBFJHSBJHFB","avaialble","$30.0"));

        menuItemArraylist.add(new MenuItem("Burger","hjfbsjhHBFJHSBJHFB","avaialble","$30.0"));
        menuItemArraylist.add(new MenuItem("Burger","hjfbsjhHBFJHSBJHFB","avaialble","$30.0"));
        menuItemArraylist.add(new MenuItem("Burger","hjfbsjhHBFJHSBJHFB","avaialble","$30.0"));
        menuItemArraylist.add(new MenuItem("Burger","hjfbsjhHBFJHSBJHFB","avaialble","$30.0"));
        menuItemArraylist.add(new MenuItem("Burger","hjfbsjhHBFJHSBJHFB","avaialble","$30.0"));

        menuItemArraylist.add(new MenuItem("Burger","hjfbsjhHBFJHSBJHFB","avaialble","$30.0"));
        menuItemArraylist.add(new MenuItem("Burger","hjfbsjhHBFJHSBJHFB","avaialble","$30.0"));
        menuItemArraylist.add(new MenuItem("Burger","hjfbsjhHBFJHSBJHFB","avaialble","$30.0"));
        menuItemArraylist.add(new MenuItem("Burger","hjfbsjhHBFJHSBJHFB","avaialble","$30.0"));
        menuItemArraylist.add(new MenuItem("Burger","hjfbsjhHBFJHSBJHFB","avaialble","$30.0"));

        RecyclerView recyclerView = view.findViewById(R.id.adminMenuItemRecyclerView);

        AdminMenuEditAdapter adminMenuEditAdapter = new AdminMenuEditAdapter(getContext(),menuItemArraylist);
        recyclerView.setAdapter(adminMenuEditAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

}