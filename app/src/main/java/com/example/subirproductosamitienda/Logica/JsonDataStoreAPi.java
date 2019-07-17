package com.example.subirproductosamitienda.Logica;

import com.example.subirproductosamitienda.model.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface JsonDataStoreAPi {

    @POST("/productos")
    Call<List<Producto>> getProductos();
}
