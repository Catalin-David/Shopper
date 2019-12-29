package com.example.shopper;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shopper.Models.Order;
import com.example.shopper.PaymentFailureFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartThirdFragment extends Fragment {
    private static final String TAG = "CartThirdFragment";

    private TextView txtPrice, txtShippingDetails;
    private Button btnBack, btnNext;
    private RadioGroup rgPaymentMethod;
    private Order incomingOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third_cart, container, false);
        initViews(view);
        Bundle bundle = getArguments();
        try{
            incomingOrder = bundle.getParcelable("order");
        }catch (NullPointerException e){
            e.printStackTrace();
        }


        if(incomingOrder != null){
            txtPrice.setText(String.valueOf(incomingOrder.getTotalPrice()));
            String finalString = "Items:\n\tAddress: " + incomingOrder.getAddress() + "\n\t"
                    + "Email: " + incomingOrder.getEmail() + "\n\t"
                    + "Zip code: " + incomingOrder.getZipCode() + "\n\t"
                    + "Phone number: " + incomingOrder.getPhoneNumber() + "\n\t";
            txtShippingDetails.setText(finalString);

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToPayment();
                }
            });

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBack();
                }
            });
        }

        return view;
    }

    private void goBack(){
        Log.d(TAG, "goBack: started");

        Order order = new Order();
        order.setTotalPrice(incomingOrder.getTotalPrice());
        order.setItems(incomingOrder.getItems());

        Bundle bundle = new Bundle();
        bundle.putParcelable("order", order);
        CartSecondFragment cartSecondFragment = new CartSecondFragment();
        cartSecondFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.out, R.anim.in)
                .replace(R.id.fragment_container, cartSecondFragment).commit();
    }

    private void goToPayment(){
        Log.d(TAG, "goToPayment: started");

        RadioButton radioButton = getActivity().findViewById(rgPaymentMethod.getCheckedRadioButtonId());
        incomingOrder.setPaymentMethod(radioButton.getText().toString());
        incomingOrder.setSuccess(true);

        goToPaymentResult(incomingOrder);
    }

    private void goToPaymentResult(Order order){
        Log.d(TAG, "goToPaymentResult: started");

        if(order.isSuccess()){
            Bundle bundle = new Bundle();
            bundle.putParcelable("order", order);

            PaymentSuccessFragment successFragment = new PaymentSuccessFragment();
            successFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.in, R.anim.out)
                    .replace(R.id.fragment_container, successFragment).commit();
        }else{
            Bundle bundle = new Bundle();
            bundle.putParcelable("order", order);

            PaymentFailureFragment failureFragment = new PaymentFailureFragment();
            failureFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.in, R.anim.out)
                    .replace(R.id.fragment_container, failureFragment).commit();
        }
    }

    void initViews(View view){
        txtPrice = view.findViewById(R.id.txtPrice);
        txtShippingDetails = view.findViewById(R.id.txtShippingDetails);
        btnBack = view.findViewById(R.id.btnBack);
        btnNext = view.findViewById(R.id.btnFinish);
        rgPaymentMethod = view.findViewById(R.id.rgPayment);
    }
}
