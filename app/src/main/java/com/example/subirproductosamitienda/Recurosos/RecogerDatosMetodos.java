package com.example.subirproductosamitienda.Recurosos;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public interface RecogerDatosMetodos {


    public boolean checkUsername(EditText editText);
    public boolean checkpassword(EditText editText);
    public  boolean isInteger(EditText editText);
    public boolean isNull(EditText editText);
}
