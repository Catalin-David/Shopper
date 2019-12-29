package com.example.shopper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.shopper.Models.Order;

public class CartSecondFragment extends Fragment {
    private static final String TAG = "CartSecondFragment";

    private EditText edtTxtAddress, edtTextZipcode, edtTextPhone, edtTextEmail;
    private Button btnCancel, btnNext;
    private Order incomingOrder;
    private ConstraintLayout parent, addressConstLayout, emailConstLayout, phoneConstLayout, zipConstLayout;
    private NestedScrollView nestedScrollView;

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
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in, R.anim.out)
                        .replace(R.id.fragment_container, new CartFirstFragment()).commit();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData()){
                    passData();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please fill all the blanks with your valid data")
                            .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.create().show();
                }
            }
        });

        initConstLayouts();

        return view;
    }

    private void initConstLayouts(){
        Log.d(TAG, "initConstLayouts: started");

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
            }
        });

        addressConstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
            }
        });

        emailConstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
            }
        });

        phoneConstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
            }
        });

        zipConstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
            }
        });

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                closeKeyboard();
            }
        });
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
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.out, R.anim.in)
                .replace(R.id.fragment_container, thirdFragment).commit();
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
        parent = view.findViewById(R.id.parent);
        addressConstLayout = view.findViewById(R.id.addressConstLayout);
        emailConstLayout = view.findViewById(R.id.emailConstLayout);
        zipConstLayout = view.findViewById(R.id.zipConstLayout);
        phoneConstLayout = view.findViewById(R.id.numberConstLayout);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);
    }

    private void closeKeyboard(){
        View view = getActivity().getCurrentFocus();
        if (null != view){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
