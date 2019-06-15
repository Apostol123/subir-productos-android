package com.example.subirproductosamitienda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.subirproductosamitienda.Recurosos.RecogerDatosMetodos;
import com.example.subirproductosamitienda.Recurosos.RecursoRecogerDatos;
import com.example.subirproductosamitienda.vista.LoginScreen;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        RecursoRecogerDatos.getInstance().setFragmentManager(fragmentManager);
        RecursoRecogerDatos.getInstance().setFirebaseAuth(firebaseAuth);



        fragmentManager.beginTransaction().replace(R.id.frame_layout, new LoginScreen()).addToBackStack(null).commit();



    }
}
