package com.example.subirproductosamitienda.vista;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.subirproductosamitienda.Logica.JsonPlaceHolderAPi;
import com.example.subirproductosamitienda.Logica.Post;
import com.example.subirproductosamitienda.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProvaPost extends Fragment {

    private TextView textView;


    public ProvaPost() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_prova_post, container, false);

        textView=view.findViewById(R.id.provaPost);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderAPi jsonPlaceHolderAPi = retrofit.create(JsonPlaceHolderAPi.class);

        Call<List<Post>> call=jsonPlaceHolderAPi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    textView.setText("Code: "+response.code());
                    return;
                }

                List<Post> posts=response.body();

                for (Post post:posts){
                    String content="";
                    content+="id: "+post.getText() +"\n";
                    textView.append(content);
                }


            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                    textView.setText(t.getMessage());
            }
        });


        return view;
    }

}
