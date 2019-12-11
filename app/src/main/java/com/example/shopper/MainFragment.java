package com.example.shopper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopper.Models.GroceryItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";

    private RecyclerView newItemsRecView, popularItemsRecView, suggestedRecView;
    private BottomNavigationView bottomNavigationView;

    private GroceryItemAdapter newItemsAdapter, popularItemsAdapter, suggestedAdapter;
    private Utils utils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(view);
        initBottomNavigation();

        utils = new Utils();
        utils.initDatabase(getActivity());

        initRecViews();

        return view;
    }

    private void initRecViews(){
        newItemsAdapter = new GroceryItemAdapter(getActivity());
        popularItemsAdapter = new GroceryItemAdapter(getActivity());
        suggestedAdapter = new GroceryItemAdapter(getActivity());

        newItemsRecView.setAdapter(newItemsAdapter);
        popularItemsRecView.setAdapter(popularItemsAdapter);
        suggestedRecView.setAdapter(suggestedAdapter);

        newItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        suggestedRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        ArrayList<GroceryItem> allitems = utils.getAllItems(getActivity());
        if (null!=allitems){
            newItemsAdapter.setItems(allitems);
        }
    }

    private void initBottomNavigation(){
        bottomNavigationView.setSelectedItemId(R.id.homeActivity);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.search:
                        
                        break;
                    case R.id.homeActivity:

                        break;
                    case R.id.cart:
                        Toast.makeText(getActivity(), "Cart selected", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initViews(View view){
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        newItemsRecView = view.findViewById(R.id.recViewNewItems);
        popularItemsRecView = view.findViewById(R.id.recViewPopularItems);
        suggestedRecView = view.findViewById(R.id.recViewSuggested);
    }
}
