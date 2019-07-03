package com.example.subirproductosamitienda.Logica;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderAPi {

    @GET("posts")
    Call<List<Post>> getPosts();
}
