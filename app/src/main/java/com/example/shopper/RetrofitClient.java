package com.example.shopper;

import com.example.shopper.Models.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitClient {

    @POST("post")
    Call<Order> goToFakePayment(@Body Order order);
}
