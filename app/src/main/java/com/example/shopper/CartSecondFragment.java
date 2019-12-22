package com.example.shopper;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shopper.Models.Order;

public class CartSecondFragment extends Fragment {
    private static final String TAG = "CartSecondFragment";

    private EditText edtTxtAddress, edtTextZipcode, edtTextPhone, edtTextEmail;
    private Button btnCancel, btnNext;
    private Order incomingOrder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_cart, container, false);

        Bundle bundle = getArguments();
        if(null != bundle){
            incomingOrder = bundle.getParcelable("order");
        }

        initViews(view);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFirstFragment()).commit();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData()){
                    passData();
                }
            }
        });

        return view;
    }

    private void passData(){
        Log.d(TAG, "passData: started");
        Bundle bundle = new Bundle();
        incomingOrder.setAddress(edtTxtAddress.getText().toString());
        incomingOrder.setEmail(edtTextEmail.getText().toString());
        incomingOrder.setPhoneNumber(edtTextPhone.getText().toString());
        incomingOrder.setZipCode(edtTextZipcode.getText().toString());

        bundle.putParcelable("order", incomingOrder);

        CartThirdFragment thirdFragment = new CartThirdFragment();
        thirdFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, thirdFragment).commit();
    }

    private boolean validateData(){
        Log.d(TAG, "validateData: started");

        if(edtTxtAddress.getText().toString().equals("")){
            return false;
        }
        if(edtTextEmail.getText().toString().equals("")){
            return false;
        }
        if(edtTextPhone.getText().toString().equals("")){
            return false;
        }
        if(edtTextZipcode.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    private void initViews(View view){
        Log.d(TAG, "initViews: started");

        edtTxtAddress = view.findViewById(R.id.edtTextAddress);
        edtTextZipcode = view.findViewById(R.id.edtTextZipcode);
        edtTextPhone = view.findViewById(R.id.edtTextPhone);
        edtTextEmail = view.findViewById(R.id.edtTextEmail);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnNext = view.findViewById(R.id.btnNext);
    }
}
