package com.example.anupama.mvvm.Network;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiServices {

    @Headers("Content-Type: application/json")
    @GET("/moments")
    Call<JsonElement> getMoments(@Query("page") String page,
                                 @Query("per_page") String perpage,
                                 @Query("moment_type") String momenttype,
                                 @Header("X-User-Token")String access_token );


    @Headers("Content-Type: application/json")
    @GET("/notifications")
    Call<JsonElement> getNotifications(@Header("X-User-Token")String access_token);

}
