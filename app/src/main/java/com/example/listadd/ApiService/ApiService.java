package com.example.listadd.ApiService;

import com.example.listadd.model.Product;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import java.util.List;

public interface ApiService {
    @GET("public/get")
    Call<List<Product>> getProducts();


  @Multipart
  @POST("public/add")
    Call<ResponseBody> addProduct(
            @Part("product_name") RequestBody productName,
            @Part("product_type") RequestBody productType,
            @Part("price") RequestBody price,
            @Part("tax") RequestBody tax,
            @Part MultipartBody.Part image
    );



}
