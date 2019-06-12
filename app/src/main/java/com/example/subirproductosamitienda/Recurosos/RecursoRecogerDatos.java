package com.example.subirproductosamitienda.Recurosos;

import android.os.Build;
import android.service.autofill.RegexValidator;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.regex.Pattern;

public class RecursoRecogerDatos {

    private static final Pattern sPattern
            = Pattern.compile("[a-zA-Z]");

    private  static  final  RecursoRecogerDatos instance = new RecursoRecogerDatos();

    private RecursoRecogerDatos(){

    }

    public static RecursoRecogerDatos getInstance(){
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean soloLetras(Editable editable){
        return editable.chars().allMatch(Character::isLetter);
    }
    public int checkUsernameLength(Editable editable){
        return  editable.toString().length();
    }

}
