package com.example.restauyou.CustomerFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.restauyou.CustomerAdapters.MenuFilterAdapter;
import com.example.restauyou.CustomerAdapters.MenuAdapter;
import com.example.restauyou.CustomerHomePageActivity;
import com.example.restauyou.ModelClass.MenuFilter;
import com.example.restauyou.ModelClass.MenuItem;
import com.example.restauyou.R;

import java.util.ArrayList;

public class CustomerHomeFragment extends Fragment {
    RecyclerView chooseRv, displayRv;
    ImageButton cartBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cusomter_home, container, false);

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

        ArrayList<MenuItem> foods = new ArrayList<>();
        for (int i = 1; i <= 10; i++)
            foods.add(new MenuItem("Burger " + i, "This is a standard Burger ", "Available", "$99.99", 0, R.drawable.burger_img, false));


        // Set adapter
        displayRv.setAdapter(new MenuAdapter(getContext(), foods));
        chooseRv.setAdapter(new MenuFilterAdapter(getContext(), choose));

        // Set layout
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        chooseRv.setLayoutManager(llm);

        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        displayRv.setLayoutManager(llm);

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
}