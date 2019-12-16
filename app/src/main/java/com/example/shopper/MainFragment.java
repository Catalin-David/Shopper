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
import java.util.Collections;
import java.util.Comparator;

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

        utils = new Utils(getActivity());
        utils.initDatabase();

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
        updateRecViews();
    }

    private void updateRecViews(){
        ArrayList<GroceryItem> newItems = utils.getAllItems();

        Comparator<GroceryItem> newItemComparator = new Comparator<GroceryItem>() {
            @Override
            public int compare(GroceryItem o1, GroceryItem o2) {
                return o2.getId() - o1.getId();
            }
        };
        Collections.sort(newItems, newItemComparator);
        if (null!=newItems){
            newItemsAdapter.setItems(newItems);
        }

        ArrayList<GroceryItem> popularItems = utils.getAllItems();
        Comparator<GroceryItem> popularityComparator = new Comparator<GroceryItem>() {
            @Override
            public int compare(GroceryItem o1, GroceryItem o2) {
                return compareByPopularity(o1, o2);
            }
        };
        Comparator<GroceryItem> reverseComp = Collections.reverseOrder(popularityComparator);
        Collections.sort(popularItems, reverseComp);
        popularItemsAdapter.setItems(popularItems);

        ArrayList<GroceryItem> suggestedItems = utils.getAllItems();
        Comparator<GroceryItem> suggestedItemsComparator = new Comparator<GroceryItem>() {
            @Override
            public int compare(GroceryItem o1, GroceryItem o2) {
                return o2.getUserPoint() - o1.getUserPoint();
            }
        };
        Collections.sort(suggestedItems, suggestedItemsComparator);
        suggestedAdapter.setItems(suggestedItems);
    }

    @Override
    public void onResume() {
        updateRecViews();
        super.onResume();
    }

    private int compareByPopularity(GroceryItem a, GroceryItem b){
        if(a.getPopularityPoint() > b.getPopularityPoint()){
            return 1;
        }else if(a.getPopularityPoint() < b.getPopularityPoint()){
            return -1;
        }else
        {
            return 0;
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
