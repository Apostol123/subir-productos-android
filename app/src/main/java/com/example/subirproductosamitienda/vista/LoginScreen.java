package com.example.subirproductosamitienda.vista;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subirproductosamitienda.R;
import com.example.subirproductosamitienda.Recursos.RecursoRecogerDatos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mobsandgeeks.saripaar.Validator;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginScreen extends Fragment  {
    private  Validator validator;
    private  EditText editTextnombreUsuario;
    private  EditText editTextpassword;
    private Button btnLogin;
    private RecursoRecogerDatos recursoRecogerDatos;
    private boolean usuarioCorrecto=false;
    private boolean passCorrecto=false;
    private FirebaseAuth firebaseAuth;
    private TextView textViewRegistro;


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

        firebaseAuth =RecursoRecogerDatos.getInstance().getFirebaseAuth();





        return view;
    }


    private void initView(View view) {
        editTextnombreUsuario = view.findViewById(R.id.etusername);
        editTextpassword = view.findViewById(R.id.etpassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        textViewRegistro=view.findViewById(R.id.tvRegistro);

        textViewRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecursoRecogerDatos.getInstance().getFragmentManager().beginTransaction().replace(R.id.frame_layout, new Registro()).addToBackStack(null).commit();

            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (passCorrecto&&usuarioCorrecto){
                    firebaseAuth.signInWithEmailAndPassword(editTextnombreUsuario.getText().toString(),editTextpassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        RecursoRecogerDatos.getInstance().setCurrentUser(firebaseAuth.getCurrentUser());
                                        RecursoRecogerDatos.getInstance().getFragmentManager().beginTransaction().replace(R.id.frame_layout, new MainMenuFragment()).addToBackStack(null).commit();

                                    } else {
                                        Toast.makeText(getContext(),"Error comprueba tus datos",Toast.LENGTH_LONG).show();
                                    }
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
                    if(!RecursoRecogerDatos.getInstance().checkEmail(editable)){
                        editTextnombreUsuario.setError("Correo no valido!");

                    }else {
                        usuarioCorrecto=true;
                    }
                } else if(editable==editTextpassword.getEditableText()){
                    if(!recursoRecogerDatos.soloLetras(editable)){
                        editTextpassword.setError(getString(R.string.errorSoloLetras));

                    }else if(editable.toString().length()<7){
                        editTextpassword.setError("Contraseña demasiado corta minimo 7 caracteres");

                    }else if(editable.toString().length()>15){
                        editTextpassword.setError("Contraseña demasiado larga maximo 15 caracteres");
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
