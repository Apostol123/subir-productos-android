package com.example.subirproductosamitienda.Recursos;

import android.content.SharedPreferences;
import android.os.Build;
import android.text.Editable;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;

import com.example.subirproductosamitienda.Logica.Retorifit_Singleton_Connection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class RecursoRecogerDatos {

    private static final Pattern sPattern
            = Pattern.compile("[a-zA-Z]");
    public  SharedPreferences sharedPreferences;

    private  static final  Pattern emailPattern = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");

    private  static  final  RecursoRecogerDatos instance = new RecursoRecogerDatos();
    private FirebaseUser currentUser;
    public static final int NUM_OF_COLUMNS=2;

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(FirebaseUser currentUser) {
        this.currentUser = currentUser;
    }

    private  FirebaseAuth firebaseAuth;

    public void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public  FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    private RecursoRecogerDatos(){

    }

    public static RecursoRecogerDatos getInstance(){
        return instance;
    }
    private FragmentManager fragmentManager;

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean soloLetras(Editable editable){
        return editable.chars().allMatch(Character::isLetter);
    }
    public int checkUsernameLength(Editable editable){
        return  editable.toString().length();
    }

        public boolean checkEmail(Editable editable){
        return emailPattern.matcher(editable.toString()).matches();
        }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }
}
