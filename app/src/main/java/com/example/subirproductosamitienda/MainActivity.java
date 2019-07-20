package com.example.subirproductosamitienda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.subirproductosamitienda.Logica.Session.Session;
import com.example.subirproductosamitienda.Recursos.RecursoRecogerDatos;
import com.example.subirproductosamitienda.vista.LoginScreen;
import com.example.subirproductosamitienda.vista.MainMenuFragment;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private final String PREFERENCE_FILE_KEY = "myAppPreference";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPref = this.getSharedPreferences(
                PREFERENCE_FILE_KEY, this.MODE_PRIVATE);


      RecursoRecogerDatos.getInstance().setSharedPreferences(sharedPref);



        FragmentManager fragmentManager = getSupportFragmentManager();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        RecursoRecogerDatos.getInstance().setFragmentManager(fragmentManager);
        RecursoRecogerDatos.getInstance().setFirebaseAuth(firebaseAuth);


        if (RecursoRecogerDatos.getInstance().getSharedPreferences().getString(Integer.toString(R.string.TOKEN),"").isEmpty())
        fragmentManager.beginTransaction().replace(R.id.frame_layout, new LoginScreen()).addToBackStack(null).commit();
        else  {
            fragmentManager.beginTransaction().replace(R.id.frame_layout, new MainMenuFragment()).addToBackStack(null).commit();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

           Fragment fragment= RecursoRecogerDatos.getInstance().getFragmentManager().findFragmentByTag("upload");
            fragment.onActivityResult(requestCode,resultCode,data);
    }


    private boolean sessionOn(){
        return RecursoRecogerDatos.getInstance().getCurrentUser()!=null;
    }

}
