package com.example.coffeeappmanage.api;


import androidx.annotation.AnyRes;

import retrofit2.Call;
import retrofit2.Retrofit;

import com.example.coffeeappmanage.model.ResponseCountRate;
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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
//            .baseUrl("http://10.0.2.2:3000/")
//            .baseUrl("http://192.168.137.1:3000/")
//            .baseUrl("http://192.168.86.1:3000/")
            .baseUrl("http://10.12.2.66:3000/")
//            .baseUrl("http://192.168.1.67:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);


    @POST("users")
    Call<User> createUser(@Body User user);

    @GET("users")
    Call<ResponseUser> getAllUser();


    @GET("users/check-email")
    Call<Boolean> checkEmailExists(@Query("email") String email);

//    @GET("users/get-user-from-email")
//    Call<ResponseUser> getUserFromEmail(@Query("email") String email);

    @FormUrlEncoded
    @PUT("users/change-password/{id_user}")
    Call<Void> changePassword(@Path("id_user") int id_user,
                              @Field("new_password") String new_password);




    // ca phe
    @GET("product/caphe")
    Call<ResponseProduct> getAllCoffee();

    @GET("product/caphe/sortCost")
    Call<ResponseProduct> getCoffeeFilterCost();

    @GET("product/caphe/sortSale")
    Call<ResponseProduct> getCoffeeFilterSale();

    @GET("product/caphe/sortRate")
//    Call<ResponseRateProduct> getCoffeeFilterRate();
    Call<ResponseProduct> getCoffeeFilterRate();

    // tra sua
    @GET("product/trasua")
    Call<ResponseProduct> getAllTraSua();

    @GET("product/trasua/sortCost")
    Call<ResponseProduct> getTraSuaFilterCost();

    @GET("product/trasua/sortSale")
    Call<ResponseProduct> getTraSuaFilterSale();

    @GET("product/trasua/sortRate")
//    Call<ResponseRateProduct> getTraSuaFilterRate();
    Call<ResponseProduct> getTraSuaFilterRate();


    // sinh to
    @GET("product/sinhto")
    Call<ResponseProduct> getAllSinhTo();

    @GET("product/sinhto/sortCost")
    Call<ResponseProduct> getSinhToFilterCost();

    @GET("product/sinhto/sortSale")
    Call<ResponseProduct> getSinhToFilterSale();

    @GET("product/sinhto/sortRate")
//    Call<ResponseRateProduct> getSinhToFilterRate();
    Call<ResponseProduct> getSinhToFilterRate();



    // topping
    @GET("topping")
    Call<ResponseTopping> getAllTopping();



    // rate
    @GET("rate/{id_product}/count_rate")
    Call<ResponseCountRate> getCountRateFromId(@Path("id_product") int id_product);

    @FormUrlEncoded
    @POST("rate")
    Call<Void> insertRate(@Field("id_product") int id_product,
                            @Field("id_user") int id_user,
                            @Field("soLuongSao") float soLuongSao,
                            @Field("comment") String comment);


    @FormUrlEncoded
    @POST("donhang")
    Call<Void> themVaoGioHang(@Field("id_user") int id_user,
                              @Field("id_product") int id_product,
                              @Field("status") String status,
                              @Field("giaDonHang") float giaDonHang);


    @FormUrlEncoded
    @POST("product-topping")
    Call<Void> insertProductTopping(@Field("productId") int productId,
                                    @Field("toppingId") int toppingId);
}
