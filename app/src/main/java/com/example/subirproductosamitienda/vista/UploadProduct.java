package com.example.subirproductosamitienda.vista;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subirproductosamitienda.BuildConfig;
import com.example.subirproductosamitienda.Logica.Logica_Upload_Product;
import com.example.subirproductosamitienda.MainActivity;
import com.example.subirproductosamitienda.R;
import com.example.subirproductosamitienda.Recursos.FileProvider;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.subirproductosamitienda.Logica.Logica_Upload_Product.CAMER_REQUEST_CODE;
import static com.example.subirproductosamitienda.Logica.Logica_Upload_Product.GALLERY_REQUEST_CODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadProduct extends Fragment {

        private TextView seleccionarFoto;
        private TextView tomarFoto;
        private ImageView imageView;
        private Logica_Upload_Product logica_upload_product;
        private EditText editTextPrecioProducto;
        private CheckBox tarifas;
        private EditText editTextTarifasPrducto;
        private View lineaTarifas;
        private LinearLayout mRootLinearLayout;
        private EditText editTextPrecioTarifas;
        private EditText precioTarifasPersonalizdas;
        private TextWatcher textWatcherTarifas;
        private   LinearLayout linearLayoutTarifas;
    private   LinearLayout linearLayoutPreciosTarifas;

    private Bitmap mImageBitmap;
    private EditText getEditTextPrecioTarifas;




    public UploadProduct() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_upload_product, container, false);
       initData(view);
       logica_upload_product=new Logica_Upload_Product(this);

       logica_upload_product.dexterPermission();

        return  view;
    }

    private void initData(View view) {
            seleccionarFoto=view.findViewById(R.id.tvSeleccionarFoto);
            imageView=(ImageView) view.findViewById(R.id.ivfotoProducto);
            tomarFoto=view.findViewById(R.id.tvtomar);
            tarifas=(CheckBox)view.findViewById(R.id.checkbtnTarifasProducto);
            editTextTarifasPrducto=(EditText)view.findViewById(R.id.etTarifasProducto);
            lineaTarifas=(View)view.findViewById(R.id.linea_tarifas);
            mRootLinearLayout = (LinearLayout) view.findViewById(R.id.rootLinearLayout);
            linearLayoutPreciosTarifas=(LinearLayout)view.findViewById(R.id.layout_precios_tarifas);
            editTextPrecioProducto=(EditText)view.findViewById(R.id.etPrecioProducto);


            seleccionarFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                 logica_upload_product.pickFromGallery();


                }
            });

            tomarFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   logica_upload_product.captureFromCamera();
                }
            });


            tarifas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){

                        onAddFieldNrTarifas(view);

                    }else {

                            onRemoveView(view);


                    }
                }
            });
    }




    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        // Result code is RESULT_OK only if the user selects an Image

        switch (requestCode){
            case GALLERY_REQUEST_CODE:
                System.out.println("wen Gallery");
                //data.getData return the content URI for the selected Image
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                // Get the cursor
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
                //Get the column index of MediaStore.Images.Media.DATA
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                //Gets the String value in the column
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String

                imageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                if (imageView.getDrawable()!=null){
                    imageView.setVisibility(View.VISIBLE);
                }
                Toast.makeText(getContext(),imageView.getDrawable().toString(),Toast.LENGTH_LONG).show();
                break;


            case CAMER_REQUEST_CODE:
                try {
                    Toast.makeText(getContext(),"CAMERA",Toast.LENGTH_SHORT).show();
                    mImageBitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), Uri.parse(logica_upload_product.getCameraFilePath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(mImageBitmap);
                if (imageView.getDrawable()!=null){
                    imageView.setVisibility(View.VISIBLE);
                }

                break;

        }
    }




    private TextWatcher setTextChangeListeners(View view){
        TextWatcher textWatcherTarifas = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    int nr_tarifas = Integer.parseInt(editable.toString());

                    System.out.println("tarifas " + nr_tarifas);
                    for (int i = 0; i < nr_tarifas; i++) {

                        editTextPrecioTarifas = new EditText(getActivity());
                        editTextPrecioTarifas.setHint("Tarifa nr: " + (i+1));
                        editTextPrecioTarifas.setHintTextColor(Color.WHITE);
                        editTextPrecioTarifas.setTextColor(Color.WHITE);
                        linearLayoutPreciosTarifas.addView(editTextPrecioTarifas);


                        View linea = new View(getActivity());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                        linea.setLayoutParams(params);
                        linea.setBackgroundColor(Color.WHITE);
                        linearLayoutPreciosTarifas.addView(linea);
                    }
                }else {
                    linearLayoutPreciosTarifas.removeAllViews();
                }



            }
        };


        return textWatcherTarifas;

    }


    private TextWatcher setTextChangeListenersTarifas(View view) {
        TextWatcher textWatcherTarifas = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void afterTextChanged(Editable editable) {



                    precioTarifasPersonalizdas = new EditText(getActivity());
                    precioTarifasPersonalizdas .setHint("Precio tarifa : " + editable.toString());
                    precioTarifasPersonalizdas .setHintTextColor(Color.WHITE);
                    precioTarifasPersonalizdas .setTextColor(Color.WHITE);
                    linearLayoutPreciosTarifas.addView(  precioTarifasPersonalizdas );

                    View linea = new View(getActivity());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                    linea.setLayoutParams(params);
                    linea.setBackgroundColor(Color.WHITE);
                    linearLayoutPreciosTarifas.addView(linea);


            }


        };
        return textWatcherTarifas;
    }


    public void onAddFieldNrTarifas(View view) {
        editTextPrecioProducto.setVisibility(View.INVISIBLE);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layout_tarifas);
        editTextPrecioTarifas = new EditText(this.getActivity());
        editTextPrecioTarifas.setHint("Indique el numero de tarifas");
        editTextPrecioTarifas.setTextColor(Color.WHITE);
        linearLayout.addView(editTextPrecioTarifas);

        View linea=new View(this.getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        linea.setLayoutParams(params);
      linea.setBackgroundColor(Color.WHITE);
        linearLayout.addView(linea);

        editTextPrecioTarifas.addTextChangedListener(setTextChangeListeners(view));


    }

    public void onAddField(View view) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layout_tarifas);
        EditText editText = new EditText(this.getActivity());
        editText.setText("tarifa");
        linearLayout.addView(editText);
    }


        public void onRemoveView(View view){
            LinearLayout linearLayout=view.findViewById(R.id.layout_tarifas);
           linearLayout.removeAllViews();
          linearLayoutPreciosTarifas.removeAllViews();
            editTextPrecioProducto.setVisibility(View.VISIBLE);



        }






}
