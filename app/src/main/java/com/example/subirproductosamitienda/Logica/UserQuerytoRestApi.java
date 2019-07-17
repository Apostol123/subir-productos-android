package com.example.subirproductosamitienda.Logica;

import com.example.subirproductosamitienda.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserQuerytoRestApi {

    @GET("users")
    Call<List<User>> getUsers();
    @GET("/users/closeSession/{userId}")
    void closeSession(String userId);
}
