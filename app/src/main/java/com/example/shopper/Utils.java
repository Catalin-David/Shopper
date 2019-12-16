package com.example.shopper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.shopper.Models.GroceryItem;
import com.example.shopper.Models.Review;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String TAG = "Utils";

    private static int ID = 0;
    public static final String DATABASE_NAME = "fake_database";

    private Context context;

    public Utils(Context context){
        this.context=context;
    }

    public void updateTheRate (GroceryItem item, int newRate){
        Log.d(TAG, "updateTheRate: started for item " + item.getName());

        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem>items = gson.fromJson(sharedPreferences.getString("allitems", null), type);
        if(null != items){
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i:items){
                if(i.getId() == item.getId()){
                    i.setRate(newRate);
                }
                newItems.add(i);
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("allitems", gson.toJson(newItems));
            editor.commit();
        }
    }

    public static int getId(){
        ID++;
        return ID;
    }

    public void addItemToCart(int id){
        Log.d(TAG, "addItemToCart: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
        ArrayList<Integer> cartItems = gson.fromJson(sharedPreferences.getString("cartitems", null), type);
        if(cartItems == null){
            cartItems = new ArrayList<>();
        }
        cartItems.add(id);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cartitems", gson.toJson(cartItems));
        editor.commit();
    }

    public ArrayList<Review> getReviewForItem(int id){
        Log.d(TAG, "getReviewForItem: started");

        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem> items = gson.fromJson(sharedPreferences.getString("allitems", null), type);

        if(null!=items){
            for(GroceryItem item: items){
                if(item.getId() == id){
                    return item.getReviews();
                }
            }
        }
        return null;
    }

    public void initDatabase(){
        Log.d(TAG, "initDatabase: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem>possibleItems = gson.fromJson(sharedPreferences.getString("allitems", ""), type);

        if(null == possibleItems){
            initAllItems();
        }
    }

    public boolean addReview (Review review){
        Log.d(TAG, "addReview: started");

        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem> items = gson.fromJson(sharedPreferences.getString("allitems", null), type);

        if(null != items){
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem item: items){
                if(item.getId() == review.getGroceryItemId()){
                    ArrayList<Review> reviews = item.getReviews();
                    reviews.add(review);
                    item.setReviews(reviews);
                }

                newItems.add(item);
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("allitems", gson.toJson(items));
            editor.commit();
            return true;
        }

        return false;
    }

    public ArrayList<GroceryItem> getAllItems(){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString("allitems", null), type);
        return allItems;
    }

    private void initAllItems(){

        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        ArrayList<GroceryItem> allItems = new ArrayList<>();

        GroceryItem iceCream = new GroceryItem("Ice Cream", "Made of fresh milk and sprinkled with chocolate chips",
                "https://images-na.ssl-images-amazon.com/images/I/510AGt0X0lL.jpg", "Food", 23, 3);

        allItems.add(new GroceryItem("Cheese", "This deluxe gourmet meat and cheese sampler features a carefully selected assortment of artisan meats, cheeses and crackers on a keepsake wooden serving tray.",
                "https://images-na.ssl-images-amazon.com/images/I/71msf7UPOCL._SL1000_.jpg", "Food", 20, 12.99));
        allItems.add(new GroceryItem("Orange juice", "Minute Maid Juice, Orange, 10 Fl Oz, 6 Ct (Pack of 2)",
                "https://images-na.ssl-images-amazon.com/images/I/51NWYvbSdbL.jpg", "Drinks", 13, 9.99));
        allItems.add(new GroceryItem("Tomato Sauce", "Light and fresh, Tomato Basil sauce is bursting with the fresh smell and taste of bright basil, juicy San Marzano tomatoes and fresh onions.",
                "https://images-na.ssl-images-amazon.com/images/I/7164iwwvy8L._SL1500_.jpg", "Food", 6, 19.99));
        allItems.add(new GroceryItem("Chicken nuggets", "Evaxo Chicken Nuggets, Frozen (5 lbs.)",
                "https://images-na.ssl-images-amazon.com/images/I/817EW5djF4L._SL1500_.jpg", "Food", 23, 24.99));
        allItems.add(new GroceryItem("Pepsi", "Pepsi splash variety pack, 8 pepsi mango, 8 pepsi lime, 8 pepsi blueberry, 24 cans/12 oz, total 288 fl oz",
                "https://images-na.ssl-images-amazon.com/images/I/611%2BiQ2o%2B5L._SL1122_.jpg", "Drinks", 94, 29.99));
        allItems.add(new GroceryItem("Shampoo", "Old Spice, Shampoo and Conditioner 2 in 1, Pure Sport for Men, 32 fl oz, Twin Pack",
                "https://images-na.ssl-images-amazon.com/images/I/81mPM0yLdFL._SL1500_.jpg", "Personal care", 29, 15.01));
        allItems.add(new GroceryItem("Deodorant", "Gives you that old man strength",
                "https://images-na.ssl-images-amazon.com/images/I/81M9MonSOaL._SL1500_.jpg", "Personal care", 68, 8.45));
        allItems.add(new GroceryItem("Chocolate", "Your girlfriend will love this!",
                "https://images-na.ssl-images-amazon.com/images/I/61S5rMToZIL._SL1000_.jpg", "Food", 45, 17.00));
        allItems.add(iceCream);

        String finalString = gson.toJson(allItems);
        editor.putString("allitems", finalString);
        editor.commit();
    }
}
