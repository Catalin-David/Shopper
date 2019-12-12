package com.example.shopper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shopper.Models.GroceryItem;
import com.example.shopper.Models.Review;

import java.util.ArrayList;

public class GroceryItemActivity extends AppCompatActivity implements AddReviewDialog.AddReview {
    private static final String TAG = "GroceryItemActivity";

    @Override
    public void onAddReviewResult(Review review) {
        Log.d(TAG, "onAddReviewResult: we are adding " + review.toString());

        utils.addReview(review);
        ArrayList<Review> reviews = utils.getReviewForItem(review.getGroceryItemId());

        if(null!=reviews){
            adapter.setReviews(reviews);
        }
    }

    private TextView txtName, txtPrice, txtDescription, txtAvailability;
    private ImageView itemImage, firstEmptyStar, firstFilledStar, secondEmptyStar, secondFilledStar, thirdEmptyStar, thirdFilledStar;
    private Button btnAddToCart;
    private RecyclerView reviewsRecView;
    private ReviewsAdapter adapter;
    private ConstraintLayout addReviewConstLayout;
    private GroceryItem incomingItem;
    private Utils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);

        utils = new Utils(this);

        initViews();

        Intent intent = getIntent();
        try{
            incomingItem = intent.getParcelableExtra("item");
            setViewsValues();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    /*
    responsible for setting the initial views values
     */
    private void setViewsValues(){
        txtName.setText(incomingItem.getName());
        txtAvailability.setText(String.valueOf(incomingItem.getAvailableAmount()) + " in stock");
        txtPrice.setText(String.valueOf(incomingItem.getPrice()) + "$");
        txtDescription.setText(incomingItem.getDescription());
        Glide.with(this)
                .asBitmap()
                .load(incomingItem.getImageUrl())
                .into(itemImage);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add item to the cart
            }
        });

        //TODO: handle the star situation

        adapter = new ReviewsAdapter();
        reviewsRecView.setAdapter(adapter);
        reviewsRecView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Review> reviews = utils.getReviewForItem(incomingItem.getId());
        if(null != reviews){
            adapter.setReviews(reviews);
        }

        addReviewConstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddReviewDialog dialog = new AddReviewDialog();
                Bundle bundle = new Bundle();
                bundle.putParcelable("item", incomingItem);
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "add review dialog");
            }
        });
    }

    private void initViews(){
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);
        txtAvailability = findViewById(R.id.txtAvailability);
        itemImage = findViewById(R.id.itemImage);
        firstEmptyStar = findViewById(R.id.firstEmptyStar);
        firstFilledStar = findViewById(R.id.firstFullStar);
        secondEmptyStar = findViewById(R.id.secondEmptyStar);
        secondFilledStar = findViewById(R.id.secondFullStar);
        thirdEmptyStar = findViewById(R.id.thirdEmptyStar);
        thirdFilledStar = findViewById(R.id.thirdFullStar);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        reviewsRecView = findViewById(R.id.reviewsRecView);
        addReviewConstLayout = findViewById(R.id.addReviewConstLayout);
    }
}
