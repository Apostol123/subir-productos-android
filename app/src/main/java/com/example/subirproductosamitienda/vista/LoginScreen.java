package com.example.subirproductosamitienda.vista;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.subirproductosamitienda.R;
import com.example.subirproductosamitienda.Recurosos.RecursoRecogerDatos;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginScreen extends Fragment  {
    private  Validator validator;
    private  EditText editTextnombreUsuario;
    private  EditText editTextpassword;
    private Button btnLogin;
    private RecursoRecogerDatos recursoRecogerDatos;

    public LoginScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_screen,container,false);
        recursoRecogerDatos=RecursoRecogerDatos.getInstance();

        initView(view);
        this.setTextChangeListeners();




        return view;
    }


    private void initView(View view){
            editTextnombreUsuario=view.findViewById(R.id.etusername);
        editTextpassword=view.findViewById(R.id.etpassword);
        btnLogin=view.findViewById(R.id.btnLogin);




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonLogin_onClick(view);
            }
        });
    }

    private void buttonLogin_onClick(View view){

        String username = editTextnombreUsuario.getText().toString();
        if(username.equalsIgnoreCase("pmk")){
                editTextnombreUsuario.setError("username alredy eixists");
        }


    }

        private void setTextChangeListeners(){
            editTextnombreUsuario.addTextChangedListener(new TextWatcher() {
                private CharSequence mtext;
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mtext=charSequence.toString();

                }

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void afterTextChanged(Editable editable) {
                    if(!recursoRecogerDatos.soloLetras(editable)){
                            editTextnombreUsuario.setError("nombre de usuario solo con letras [a-zA-Z]");
                    } else if (editable.toString().length()<7 ){
                        editTextnombreUsuario.setError("nombre de usuario demasiado corto minimo 7 caracteres");
                    } else if(editable.toString().length()>15){
                        editTextnombreUsuario.setError("nombre de usuario demasiado largo maximo 15 caracteres");
                    }
                }
            });

            editTextpassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void afterTextChanged(Editable editable) {
                        if(!recursoRecogerDatos.soloLetras(editable)){
                            editTextpassword.setError(getString(R.string.errorSoloLetras));

                        }else if(editable.toString().length()<7){
                            editTextpassword.setError("Contraseña demasiado corta minimo 7 caracteres");

                    }else if(editable.toString().length()<7){
                                editTextpassword.setError("Contraseña demasiado larga maximo 15 caracteres");
                        }
                }
            });
        }




}
