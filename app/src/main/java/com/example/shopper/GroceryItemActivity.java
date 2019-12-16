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
    private int currentRate = 0;
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
            this.currentRate = incomingItem.getRate();
            changeVisibility(currentRate);
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
                utils.addItemToCart(incomingItem.getId());
            }
        });

        handleStarSituation();

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

    private void handleStarSituation(){
        Log.d(TAG, "handleStarSituation: started");

        firstEmptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfRateHasChanged(1)){
                    updateDatabase(1);
                    changeVisibility(1);
                }
            }
        });
        secondEmptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfRateHasChanged(2)){
                    updateDatabase(2);
                    changeVisibility(2);
                }
            }
        });
        thirdEmptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfRateHasChanged(3)){
                    updateDatabase(3);
                    changeVisibility(3);
                }
            }
        });
        firstFilledStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfRateHasChanged(1)){
                    updateDatabase(1);
                    changeVisibility(1);
                }
            }
        });
        secondFilledStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfRateHasChanged(2)){
                    updateDatabase(2);
                    changeVisibility(2);
                }
            }
        });
        thirdFilledStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfRateHasChanged(3)){
                    updateDatabase(3);
                    changeVisibility(3);
                }
            }
        });
    }

    private void updateDatabase(int newRate){
        Log.d(TAG, "updateDatabase: started");

        utils.updateTheRate(incomingItem, newRate);

    }

    private void changeVisibility(int newRate){
        Log.d(TAG, "changeVisibility: started");

        switch (newRate){
            case 0:
                firstFilledStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.GONE);
                firstEmptyStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                break;
            case 1:
                firstFilledStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.GONE);
                firstEmptyStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                break;
            case 2:
                firstFilledStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                firstEmptyStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                break;
            case 3:
                firstFilledStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.VISIBLE);
                firstEmptyStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private boolean checkIfRateHasChanged(int newRate){
        Log.d(TAG, "checkIfRateHasChanged: started");
        if(newRate == currentRate){
            return false;
        }
        else
        {
            return true;
        }
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
