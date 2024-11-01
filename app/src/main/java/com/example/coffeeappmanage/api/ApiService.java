package com.example.coffeeappmanage.api;


import retrofit2.Call;
import retrofit2.Retrofit;

import com.example.coffeeappmanage.model.ResponseProduct;
import com.example.coffeeappmanage.model.ResponseRateProduct;
import com.example.coffeeappmanage.model.ResponseTopping;
import com.example.coffeeappmanage.model.ResponseUser;
import com.example.coffeeappmanage.model.Topping;
import com.example.coffeeappmanage.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
//            .baseUrl("http://10.0.2.2:3000/")
//            .baseUrl("http://192.168.137.1:3000/")
//            .baseUrl("http://192.168.86.1:3000/")
            .baseUrl("http://192.168.1.67:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);


    @POST("users")
    Call<User> createUser(@Body User user);

    @GET("users")
    Call<ResponseUser> getAllUser();


    @GET("users/check-email")
    Call<Boolean> checkEmailExists(@Query("email") String email);


    // ca phe
    @GET("product/caphe")
    Call<ResponseProduct> getAllCoffee();

    @GET("product/caphe/sortCost")
    Call<ResponseProduct> getCoffeeFilterCost();

    @GET("product/caphe/sortSale")
    Call<ResponseProduct> getCoffeeFilterSale();

    @GET("product/caphe/sortRate")
    Call<ResponseRateProduct> getCoffeeFilterRate();


    // tra sua
    @GET("product/trasua")
    Call<ResponseProduct> getAllTraSua();

    @GET("product/trasua/sortCost")
    Call<ResponseProduct> getTraSuaFilterCost();

    @GET("product/trasua/sortSale")
    Call<ResponseProduct> getTraSuaFilterSale();

    @GET("product/trasua/sortRate")
    Call<ResponseRateProduct> getTraSuaFilterRate();


    // sinh to
    @GET("product/sinhto")
    Call<ResponseProduct> getAllSinhTo();

    @GET("product/sinhto/sortCost")
    Call<ResponseProduct> getSinhToFilterCost();

    @GET("product/sinhto/sortSale")
    Call<ResponseProduct> getSinhToFilterSale();

    @GET("product/sinhto/sortRate")
    Call<ResponseRateProduct> getSinhToFilterRate();



    // topping
    @GET("topping")
    Call<ResponseTopping> getAllTopping();



    // rate
}
