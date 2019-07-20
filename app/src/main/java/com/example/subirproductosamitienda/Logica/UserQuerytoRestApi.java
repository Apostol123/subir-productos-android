package com.example.subirproductosamitienda.Logica;

import com.example.subirproductosamitienda.model.User;
import com.google.android.gms.tasks.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserQuerytoRestApi {

    @GET("users")
    Call<List<User>> getUsers();
    @POST("users/closeSession")
    Call<User> closeSessionId();
    @POST("users")
    Call<User> addUser(@Body User User);

    @FormUrlEncoded
    @POST("login")
    Call<String> getUserLoginToken(@Field("user")String username, @Field("password")String password);
}
