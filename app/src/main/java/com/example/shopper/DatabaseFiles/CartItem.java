package com.example.shopper.DatabaseFiles;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items")
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    private int _id;
    private int grocery_item_id;

    public CartItem(int grocery_item_id) {
        this.grocery_item_id = grocery_item_id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public int getGrocery_item_id() {
        return grocery_item_id;
    }
}
