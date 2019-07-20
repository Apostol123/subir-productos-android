package com.example.subirproductosamitienda.Logica;

import android.content.SharedPreferences;
import android.util.Base64;

import com.example.subirproductosamitienda.R;
import com.example.subirproductosamitienda.Recursos.RecursoRecogerDatos;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Retorifit_Singleton_Connection {
    private static final String AUTH="Basic "+ Base64.encodeToString(("alex:123")
            .getBytes(),Base64.NO_WRAP);


    private static  Retorifit_Singleton_Connection _instance;

    public static Retorifit_Singleton_Connection getRetorifit_Singleton_Connection(){
        if (_instance==null){
            _instance=new Retorifit_Singleton_Connection();
        }
        return _instance;
    }

    public Retrofit getRetrofitConnectionWithToken(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);



        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request.Builder requestBuild= original.newBuilder()
                                .addHeader("Authorization", RecursoRecogerDatos.getInstance()
                                        .getSharedPreferences().getString(Integer.toString(R.string.TOKEN),""))
                                .method(original.method(),original.body());
                        Request request = requestBuild.build();
                        return chain.proceed(request);

                    }
                })
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.102:8080/")
                .addConverterFactory(GsonConverterFactory.create())

                .client(okHttpClient)

                .build();
        return  retrofit;
    }


    public Retrofit getRetrofitConnectionWithoutToken(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.102:8080/")
                .addConverterFactory(GsonConverterFactory.create())

                .build();
        return  retrofit;
    }


}
