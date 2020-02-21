package com.example.shopper.DatabaseFiles;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.shopper.Models.Review;

import java.util.List;

@Dao
public interface ReviewDao {

    @Insert
    void insert(Review review);

    @Query("SELECT * FROM reviews WHERE grocery_item_id = :id")
    List<Review> getReviewsForItem(int id);

}
