package com.example.shopper;

import android.content.Context;
import android.util.Log;

import com.example.shopper.DatabaseFiles.ShopDatabase;
import com.example.shopper.Models.GroceryItem;
import com.example.shopper.Models.Review;
import java.util.ArrayList;

public class Utils {
    private static final String TAG = "Utils";

    private static int ORDER_ID = 0;

    private static ShopDatabase database;

    public Utils(Context context) {
        database = ShopDatabase.getInstance(context);
    }

    public void updateTheRate(GroceryItem item, int newRate) {
        Log.d(TAG, "updateTheRate: started for item " + item.getName());
        item.setRate(newRate);
        database.groceryItemDao().update(item);
    }

    public static int getOrderId(){
        ORDER_ID++;
        return ORDER_ID;
    }

    public void addItemToCart(int id) {
        Log.d(TAG, "addItemToCart: started");
        database.cartItemDao().insert(id);
    }

    public ArrayList<Review> getReviewForItem(int id) {
        Log.d(TAG, "getReviewForItem: started");

        return (ArrayList<Review>) database.reviewDao().getReviewsForItem(id);
    }

    public boolean addReview(Review review) {
        Log.d(TAG, "addReview: started");

        database.reviewDao().insert(review);
        return true;
    }

    public ArrayList<GroceryItem> getAllItems() {
        return (ArrayList<GroceryItem>) database.groceryItemDao().getAllGroceryItems();
    }

    public ArrayList<GroceryItem> searchForItem(String text) {
        Log.d(TAG, "searchForItem: started");

        return (ArrayList<GroceryItem>) database.groceryItemDao().searchForItem(text);
    }

    public ArrayList<String> getThreeCategories() {
        Log.d(TAG, "getThreeCategories: started");

        ArrayList<String> categs =  (ArrayList<String>) database.groceryItemDao().getAllCategories();
        ArrayList<String> uniqueCategs = new ArrayList<>();
        int unique = 0;
        for(String cat: categs){
            if(unique == 3){
                break;
            }
            Boolean doesExist = false;
            for(String uniqueCat: uniqueCategs){
                if(cat.equals(uniqueCat)){
                    doesExist = true;
                    break;
                }
            }
            if(!doesExist){
                uniqueCategs.add(cat);
                unique++;
            }
        }
        return uniqueCategs;
    }

    public ArrayList<String> getAllCategories() {
        Log.d(TAG, "getAllCategories: started");

        ArrayList<String> categs =  (ArrayList<String>) database.groceryItemDao().getAllCategories();
        ArrayList<String> uniqueCategs = new ArrayList<>();
        for(String cat: categs){
            Boolean doesExist = false;
            for(String uniqueCat: uniqueCategs){
                if(cat.equals(uniqueCat)){
                    doesExist = true;
                    break;
                }
            }
            if(!doesExist){
                uniqueCategs.add(cat);
            }
        }
        return uniqueCategs;
    }

    public ArrayList<GroceryItem> getItemsByCategory(String category){
        Log.d(TAG, "getItemsByCategory: started");

        return (ArrayList<GroceryItem>) database.groceryItemDao().getItemByCategory(category);
    }

    public ArrayList<Integer> getCartItems(){
        Log.d(TAG, "getCartItems: started");

        return (ArrayList<Integer>) database.cartItemDao().getCartItems();
    }

    public ArrayList<GroceryItem> getItemsById(ArrayList<Integer> ids){
        Log.d(TAG, "getItemsById: started");

        int[] itemIds = new int[ids.size()];
        for(int i=0; i<ids.size(); i++){
            itemIds[i]=ids.get(i);
        }

        return (ArrayList<GroceryItem>)database.groceryItemDao().getItemsbyId(itemIds);
    }

    public ArrayList<Integer> deleteCartItem(GroceryItem item){
        Log.d(TAG, "deleteCartItem: started");

        database.cartItemDao().deleteById(item.getId());
        return (ArrayList<Integer>) database.cartItemDao().getCartItems();
    }

    public void removeCartItems(){
        Log.d(TAG, "removeCartItems: started");

        database.cartItemDao().deleteAllItems();
    }

    public void addPopularityPoints(ArrayList<Integer>cartItems){
        Log.d(TAG, "addPopularityPoints: started");

        ArrayList<GroceryItem> allItems = (ArrayList<GroceryItem>) database.groceryItemDao().getAllGroceryItems();

        for (GroceryItem item:allItems){
            for(int i:cartItems){
                if(item.getId() == i){
                    item.setPopularityPoint(item.getPopularityPoint()+1);
                    database.groceryItemDao().update(item);
                    break;
                }
            }
        }
    }

    public void increaseUserPoint(GroceryItem item, int points){
        Log.d(TAG, "increaseUserPoint: increasing points for  " + item.getName() + " with " + points);

        item.setUserPoint(item.getUserPoint()+points);
        database.groceryItemDao().update(item);
    }
}
