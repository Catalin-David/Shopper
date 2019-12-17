package com.example.shopper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopper.Models.GroceryItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private EditText searchBar;
    private ImageView searchIcon;
    private TextView firstCategory, secondCategory, thirdCategory, seeAllCategories;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;
    private GroceryItemAdapter adapter;
    private Utils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        utils = new Utils(this);
        adapter = new GroceryItemAdapter(this);

        initViews();
        initBottomNavigation();
        initCategories();
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateSearch();
            }
        });
        seeAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: show a dialog
            }
        });
    }

    private void initCategories(){
        Log.d(TAG, "initCategories: started");

        ArrayList<String> categories = utils.getThreeCategories();
        switch (categories.size()){
            case 0:
                firstCategory.setVisibility(View.GONE);
                secondCategory.setVisibility(View.GONE);
                thirdCategory.setVisibility(View.GONE);
                break;
            case 1:
                firstCategory.setText(categories.get(0));
                secondCategory.setVisibility(View.GONE);
                thirdCategory.setVisibility(View.GONE);
                break;
            case 2:
                firstCategory.setText(categories.get(0));
                secondCategory.setText(categories.get(1));
                thirdCategory.setVisibility(View.GONE);
                break;
            default:
                firstCategory.setText(categories.get(0));
                secondCategory.setText(categories.get(1));
                thirdCategory.setText(categories.get(2));
                break;
        }
    }

    private void initBottomNavigation(){
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.search:
                        break;
                    case R.id.homeActivity:
                        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.cart:
                        Toast.makeText(SearchActivity.this, "Cart selected", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initiateSearch(){
        Log.d(TAG, "initiateSearch: started");

        String text = searchBar.getText().toString();
        ArrayList<GroceryItem> items = utils.searchForItem(text);
        adapter.setItems(items);
    }

    private void initViews(){
        searchBar = findViewById(R.id.edtTextSearchBar);
        searchIcon = findViewById(R.id.btnSearch);
        firstCategory = findViewById(R.id.firstCategory);
        secondCategory = findViewById(R.id.secondCategory);
        thirdCategory = findViewById(R.id.thirdCategory);
        seeAllCategories = findViewById(R.id.btnAllCategories);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }
}
