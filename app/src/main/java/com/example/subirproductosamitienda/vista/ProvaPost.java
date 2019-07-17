package com.example.subirproductosamitienda.vista;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.subirproductosamitienda.Logica.UserQuerytoRestApi;
import com.example.subirproductosamitienda.Logica.Retorifit_Singleton_Connection;
import com.example.subirproductosamitienda.model.User;
import com.example.subirproductosamitienda.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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



        UserQuerytoRestApi userQuerytoRestApi = Retorifit_Singleton_Connection.getRetorifit_Singleton_Connection().getRetrofitConnection().create(UserQuerytoRestApi.class);

        Call<List<User>> call= userQuerytoRestApi.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
                    textView.setText("Code: "+response.code());
                    return;
                }

                List<User> users=response.body();

                for (User user:users){
                    String content="";
                    content+="FIRSTNAME "+user.getFirstName() +"\n";
                    textView.append(content);
                }


            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                    textView.setText(t.getMessage());
            }
        });


        return view;
    }

}
