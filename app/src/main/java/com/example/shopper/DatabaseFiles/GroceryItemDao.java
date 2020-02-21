package com.example.shopper.DatabaseFiles;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shopper.Models.GroceryItem;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface GroceryItemDao {

    @Insert
    void insert(GroceryItem item);

    @Update
    void update(GroceryItem item);

    @Query("SELECT * FROM grocery_items")
    List<GroceryItem> getAllGroceryItems();

    @Query("SELECT * FROM grocery_items WHERE name LIKE :name")
    List<GroceryItem> searchForItem(String name);

    @Query("SELECT category FROM grocery_items LIMIT 3")
    List<String> getThreeCategories();

    @Query("SELECT category FROM grocery_items")
    List<String> getAllCategories();

    @Query("SELECT * FROM grocery_items WHERE category = :category")
    List<GroceryItem> getItemByCategory(String category);

    @Query("SELECT * FROM grocery_items WHERE _id IN (:ids)")
    List<GroceryItem> getItemsbyId(int[] ids);

}
