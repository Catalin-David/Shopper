package com.example.shopper;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shopper.Models.GroceryItem;
import com.example.shopper.Models.Order;

import java.util.ArrayList;

public class PaymentSuccessFragment extends Fragment {
    private static final String TAG = "PaymentSuccessFragment";

    private Button btnBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_success_payment, container, false);

        Utils utils = new Utils(getContext());
        utils.removeCartItems();

        Bundle bundle = getArguments();
        try {
            Order order = bundle.getParcelable("order");
            ArrayList<Integer> itemIds = order.getItems();
            utils.addPopularityPoints(itemIds);
            ArrayList<GroceryItem> items = utils.getItemsById(itemIds);
            for(GroceryItem item : items){
                ArrayList<GroceryItem> sameCat = utils.getItemsByCategory(item.getCategory());
                for (GroceryItem i:sameCat) {
                    utils.increaseUserPoint(i, 4);
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        btnBack = view.findViewById(R.id.btnGoBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return view;
    }
}
