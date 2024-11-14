package com.example.coffeeappmanage.api;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.model.DonHang;
import com.example.coffeeappmanage.model.KhuyenMai;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.Product_1;
import com.example.coffeeappmanage.model.ResponseBoolean;
import com.example.coffeeappmanage.model.ResponseCountRate;
import com.example.coffeeappmanage.model.ResponseDonHang;
import com.example.coffeeappmanage.model.ResponseKhuyenMai;
import com.example.coffeeappmanage.model.ResponseLatestIdDonHang;
import com.example.coffeeappmanage.model.ResponseProduct;
import com.example.coffeeappmanage.model.ResponseSingleProduct;
import com.example.coffeeappmanage.model.ResponseSingleDonHang;
import com.example.coffeeappmanage.model.ResponseSingleKhuyenMai;
import com.example.coffeeappmanage.model.ResponseSingleTheLoai;
import com.example.coffeeappmanage.model.ResponseSingleTopping;
import com.example.coffeeappmanage.model.ResponseSingleUser;
import com.example.coffeeappmanage.model.ResponseTheLoai;
import com.example.coffeeappmanage.model.ResponseTopping;
import com.example.coffeeappmanage.model.ResponseUser;
import com.example.coffeeappmanage.model.TheLoai;
import com.example.coffeeappmanage.model.Topping;
import com.example.coffeeappmanage.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.60:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);


    @POST("users")
    Call<User> createUser(@Body User user);

    @GET("users")
    Call<ResponseUser> getAllUser();

    @GET("users/{id_user}")
    Call<ResponseSingleUser> getUserById(@Path("id_user") int id_user);


    @GET("users/check-email")
    Call<Boolean> checkEmailExists(@Query("email") String email);

//    @GET("users/get-user-from-email")
//    Call<ResponseUser> getUserFromEmail(@Query("email") String email);

    @FormUrlEncoded
    @PUT("users/change-password/{id_user}")
    Call<Void> changePassword(@Path("id_user") int id_user,
                              @Field("new_password") String new_password);

    @GET("product")
    Call<ResponseProduct> getAllProduct();

    @DELETE("product/delete-product/{id_product}")
    Call<Void> deleteProduct(@Path("id_product") int id_product);

    @POST("product")
    Call<ResponseSingleProduct> createProduct(@Body Product_1 product1);

    @PUT("product/update-product/{id_product}")
    Call<ResponseSingleProduct> updateProduct(@Path("id_product") int id_product,
                                              @Body Product new_product);

    @GET("product/{id_product}")
    Call<ResponseSingleProduct> getProductById(@Path("id_product") int id_product);


    // ca phe
    @GET("product/caphe/single")
    Call<ResponseProduct> getAllCoffee();

    @GET("product/caphe/sortCost")
    Call<ResponseProduct> getCoffeeFilterCost();

    @GET("product/caphe/sortSale")
    Call<ResponseProduct> getCoffeeFilterSale();

    @GET("product/caphe/sortRate")
//    Call<ResponseRateProduct> getCoffeeFilterRate();
    Call<ResponseProduct> getCoffeeFilterRate();

    // tra sua
    @GET("product/trasua/single")
    Call<ResponseProduct> getAllTraSua();

    @GET("product/trasua/sortCost")
    Call<ResponseProduct> getTraSuaFilterCost();

    @GET("product/trasua/sortSale")
    Call<ResponseProduct> getTraSuaFilterSale();

    @GET("product/trasua/sortRate")
//    Call<ResponseRateProduct> getTraSuaFilterRate();
    Call<ResponseProduct> getTraSuaFilterRate();


    // sinh to
    @GET("product/sinhto/single")
    Call<ResponseProduct> getAllSinhTo();

    @GET("product/sinhto/sortCost")
    Call<ResponseProduct> getSinhToFilterCost();

    @GET("product/sinhto/sortSale")
    Call<ResponseProduct> getSinhToFilterSale();

    @GET("product/sinhto/sortRate")
//    Call<ResponseRateProduct> getSinhToFilterRate();
    Call<ResponseProduct> getSinhToFilterRate();


    // others product
    @GET("product/others/single")
    Call<ResponseProduct> getAllOthers();

    @GET("product/others/sortCost")
    Call<ResponseProduct> getOthersFilterCost();

    @GET("product/others/sortSale")
    Call<ResponseProduct> getOthersFilterSale();

    @GET("product/others/sortRate")
    Call<ResponseProduct> getOthersFilterRate();



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
    Call<ResponseSingleDonHang> themVaoGioHang(@Field("id_user") int id_user,
                                               @Field("id_product") int id_product,
                                               @Field("soLuong") int soLuong,
                                               @Field("tuyChinh") String tuyChinh,
                                               @Field("status") String status,
                                               @Field("giaDonHang") float giaDonHang,
                                               @Field("ghiChu") String ghiChu);


    @GET("donhang")
    Call<ResponseDonHang> getAllDonHang();

    @PUT("donhang/sua-don-hang/{id_donHang}")
    Call<Void> updateDonHang(@Path("id_donHang") int id_donHang,
                             @Body DonHang update_donHang);

    @FormUrlEncoded
    @POST("product-topping")
    Call<Void> insertProductTopping(@Field("id_don_hang") int id_don_hang,
                                    @Field("topping_id") int topping_id);


    @GET("donhang/latest-id")
    Call<ResponseLatestIdDonHang> getLatestIdDonHang();


    @GET("donhang/get-by-user/{id_user}")
    Call<ResponseDonHang> getDonHangByUser(@Path("id_user") int id_user);

//    @FormUrlEncoded
//    @POST("product-topping")
//    Call<Void> insertProductTopping(@Field("productId") int productId,
//                                    @Field("toppingId") int toppingId,
//                                    @Field("userId") int userId);


//    @FormUrlEncoded
//    @POST("tuy-chinh")
//    Call<Void> insertTuyChinh(@Field("yeuCauTuyChinh") String yeuCauTuyChinh,
//                              @Field("id_don_hang") int id_don_hang);



    @GET("theloai")
    Call<ResponseTheLoai> getAllTheLoai();

    @DELETE("theloai/xoa-the-loai/{id_theLoai}")
//    Call<Void> xoaTheLoai(@Path("id_theLoai") int id_theLoai);
    Call<ResponseBoolean> xoaTheLoai(@Path("id_theLoai") int id_theLoai);


    @FormUrlEncoded
    @PUT("theloai/sua-the-loai/{id_theLoai}")
    Call<Void> updateTheLoai(@Path("id_theLoai") int id_theLoai,
                              @Field("new_tenTheLoai") String new_tenTheLoai);


    @POST("theloai")
    Call<ResponseSingleTheLoai> createTheLoai(@Body TheLoai theLoai);

//    @POST("theloai")
//    Call<Void> createTheLoai(@Body TheLoai theLoai);



    @DELETE("topping/delete-topping/{id_topping}")
    Call<Void> deleteTopping(@Path("id_topping") int id_topping);


    @POST("topping")
    Call<ResponseSingleTopping> createTopping(@Body Topping topping);

    @PUT("topping/update-topping/{id_topping}")
    Call<ResponseSingleTopping> updateTopping(@Path("id_topping") int id_topping,
                                          @Body Topping new_product);




    @GET("khuyenmai")
    Call<ResponseKhuyenMai> getAllKhuyenMai();

    @DELETE("khuyenmai/delete-khuyen-mai/{id_khuyen_mai}")
    Call<Void> deleteKhuyenMai(@Path("id_khuyen_mai") int id_khuyen_mai);

    @POST("khuyenmai")
    Call<ResponseSingleKhuyenMai> createKhuyenMai(@Body KhuyenMai khuyenMai);

    @PUT("khuyenmai/update-khuyen-mai/{id_khuyen_mai}")
    Call<ResponseSingleKhuyenMai> updateKhuyenMai(@Path("id_khuyen_mai") int id_khuyen_mai,
                                              @Body KhuyenMai update_khuyenMai);



    // uploads
    @Multipart
    @POST("product/upload")
    Call<Void> uploadImage(@Part("id_product") RequestBody id_product,
                           @Part MultipartBody.Part file);

}
