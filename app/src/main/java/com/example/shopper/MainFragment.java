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

import com.example.shopper.Models.GroceryItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";

    private BottomNavigationView bottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(view);
        initBottomNavigation();

        Utils utils = new Utils();
        utils.initDatabase(getActivity());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("fake_database", Context.MODE_PRIVATE);
        String item = sharedPreferences.getString("cheese", "");
        Gson gson = new Gson();
        GroceryItem cheese = gson.fromJson(item, GroceryItem.class);
        Toast.makeText(getActivity(), cheese.getName(), Toast.LENGTH_SHORT).show();
        return view;
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
    }
}
