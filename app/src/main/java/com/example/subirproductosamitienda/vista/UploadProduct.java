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


        private Logica_Upload_Product logica_upload_product;



    private Bitmap mImageBitmap;
    private ImageView imageView;



    public UploadProduct() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_upload_product, container, false);

       logica_upload_product=new Logica_Upload_Product(this);
       logica_upload_product.initData(view);

       logica_upload_product.dexterPermission();

        return  view;
    }




    /**
     * Funccion que permite al usuario hacer una foto o escojer
     * una foto de la galeria del movil
     * esta foto se guardara en objeto tipo IMAGEVIEW
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        // Result code is RESULT_OK only if the user selects an Image

        switch (requestCode){
            case GALLERY_REQUEST_CODE:

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

                        imageView= this.getView().findViewById(R.id.ivfotoProducto);

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
                imageView= this.getView().findViewById(R.id.ivfotoProducto);
                imageView.setImageBitmap(mImageBitmap);
                if (imageView.getDrawable()!=null){
                    imageView.setVisibility(View.VISIBLE);
                }

                break;

        }
    }













}
