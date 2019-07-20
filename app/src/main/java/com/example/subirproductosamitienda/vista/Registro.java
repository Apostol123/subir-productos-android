package com.example.subirproductosamitienda.vista;


import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.subirproductosamitienda.Logica.Retorifit_Singleton_Connection;
import com.example.subirproductosamitienda.Logica.UserQuerytoRestApi;
import com.example.subirproductosamitienda.R;
import com.example.subirproductosamitienda.Recursos.RecursoRecogerDatos;
import com.example.subirproductosamitienda.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Registro extends Fragment {
    private EditText editTextnombreUsuario;
    private EditText editTextpassword;
    private Button btnLogin;
    private boolean usuarioCorrecto=false;
    private boolean passCorrecto=false;
    private FirebaseAuth firebaseAuth;



    public Registro() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_registro, container, false);


        initView(view);
        setTextChangeListeners();


        return view;
    }

    private void initView(View view) {
        editTextnombreUsuario = view.findViewById(R.id.etusernameRegistro);
        editTextpassword = view.findViewById(R.id.etpasswordRegistro);
        btnLogin = view.findViewById(R.id.btnLoginRegistro);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usuarioCorrecto&&passCorrecto){
                    UserQuerytoRestApi userQuerytoRestApi =
                            Retorifit_Singleton_Connection
                                    .getRetorifit_Singleton_Connection().getRetrofitConnectionWithoutToken()
                                    .create(UserQuerytoRestApi.class);

                    Call<String> getuserToken =userQuerytoRestApi.getUserLoginToken(editTextnombreUsuario.getText().toString(),
                            editTextpassword.getText().toString());
                    Toast.makeText(getContext(),"HERE1",Toast.LENGTH_LONG);
                    SharedPreferences.Editor editor = RecursoRecogerDatos.getInstance().getSharedPreferences().edit();
                  getuserToken.enqueue(new Callback<String>() {
                      @Override
                      public void onResponse(Call<String> call, Response<String> response) {
                                String userToken = response.body().toString();
                          editor.putString(Integer.toString(R.string.TOKEN),userToken);
                          editor.commit();
                          RecursoRecogerDatos.getInstance().getFragmentManager().beginTransaction().replace(R.id.frame_layout, new LoginScreen()).addToBackStack(null).commit();

                      }

                      @Override
                      public void onFailure(Call<String> call, Throwable t) {

                      }
                  });


                }
            }
        });


    }

    private void setTextChangeListeners(){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable==editTextnombreUsuario.getEditableText()){
                    if(!RecursoRecogerDatos.getInstance().soloLetras(editable)){
                        editTextpassword.setError(getString(R.string.errorSoloLetras));
                        usuarioCorrecto=false;

                    }else if(editable.toString().length()<4){
                        editTextpassword.setError("Nombre de usuario  demasiado corto minimo 4 caracteres");
                        usuarioCorrecto=false;

                    }else if(editable.toString().length()>10){
                        editTextpassword.setError("Nombre de usuario  demasiado  largo maximo 10 caracteres");
                        usuarioCorrecto=false;
                    }else {
                       usuarioCorrecto=true;
                    }
                } else if(editable==editTextpassword.getEditableText()){
                    if(!RecursoRecogerDatos.getInstance().soloLetras(editable)){
                        editTextpassword.setError(getString(R.string.errorSoloLetras));
                        passCorrecto=false;

                    }else if(editable.toString().length()<7){
                        editTextpassword.setError("Contraseña demasiado corta minimo 7 caracteres");
                        passCorrecto=false;

                    }else if(editable.toString().length()>15){
                        editTextpassword.setError("Contraseña demasiado larga maximo 15 caracteres");
                        passCorrecto=false;
                    }else {
                        passCorrecto=true;
                    }

                }
                if(passCorrecto&&usuarioCorrecto){
                    Toast.makeText(getContext(),"Campos Correctos",Toast.LENGTH_SHORT).show();
                }
            }
        };
        editTextnombreUsuario.addTextChangedListener(textWatcher);
        editTextpassword.addTextChangedListener(textWatcher);



    }

}
