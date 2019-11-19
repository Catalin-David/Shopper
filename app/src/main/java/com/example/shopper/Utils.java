package com.example.shopper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.shopper.Models.GroceryItem;
import com.google.gson.Gson;

public class Utils {
    private static final String TAG = "Utils";

    private static int ID = 0;
    public static final String DATABASE_NAME = "fake_database";

    public Utils(){

    }

    public static int getId(){
        ID++;
        return ID;
    }

    public void initDatabase(Context context){
        Log.d(TAG, "initDatabase: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        GroceryItem item = new GroceryItem("Cheese", "Add a slice of happiness to your next meal with Great Value Deli Style Sliced Medium Cheddar Cheese. Each slice of cheese in this package of 12 contains 10% of your recommended daily value of calcium. The medium flavor is just the right middle ground between sharp and mild, so it's sure to satisfy. "
                                            , "", "Food", 15, 2.99);
        Gson gson = new Gson();
        String jsonFile = gson.toJson(item);
        editor.putString("cheese", jsonFile);
        editor.apply();
    }
}
