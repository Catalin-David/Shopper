package com.example.shopper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.shopper.Models.GroceryItem;
import com.example.shopper.Models.Review;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddReviewDialog extends DialogFragment {
    private static final String TAG = "AddReviewDialog";

    private EditText edtTextName, edtTextReview;
    private TextView txtItemName, txtWarning;
    private Button btnAddReview;

    private int itemId = 0;

    public interface AddReview{
        void onAddReviewResult(Review review);
    }

    private AddReview addReview;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_review, null);
        initViews(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Add Review")
                .setView(view);

        Bundle bundle = getArguments();
        try{
            GroceryItem item = bundle.getParcelable("item");
            txtItemName.setText(item.getName());
            this.itemId = item.getId();
        }catch (Exception e){
            e.printStackTrace();
        }

        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReview();
                dismiss();
            }
        });

        return builder.create();
    }

    private void addReview(){
        Log.d(TAG, "addReview: started");

        String name = edtTextName.getText().toString();
        String reviewText = edtTextReview.getText().toString();
        String date = getCurrentDate();

        Review review = new Review(itemId, name, date, reviewText);

        try{
            addReview = (AddReview) getActivity();

            addReview.onAddReviewResult(review);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    private String getCurrentDate(){
        Log.d(TAG, "getCurrentDate: started");

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        return sdf.format(date);
    }

    private void initViews(View view){
        Log.d(TAG, "initViews: started");
        edtTextName = view.findViewById(R.id.edtTextName);
        edtTextReview = view.findViewById(R.id.edtTextReview);
        txtItemName = view.findViewById(R.id.reviewName);
        txtWarning = view.findViewById(R.id.txtWarning);
        btnAddReview = view.findViewById(R.id.btnAdd);
    }
}
