package com.example.subirproductosamitienda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.subirproductosamitienda.vista.LoginScreen;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();



        fragmentManager.beginTransaction().replace(R.id.frame_layout, new LoginScreen()).addToBackStack(null).commit();



    }
}
