package com.example.subirproductosamitienda.Logica;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.subirproductosamitienda.BuildConfig;
import com.example.subirproductosamitienda.R;
import com.example.subirproductosamitienda.Recursos.FileProvider;
import com.example.subirproductosamitienda.model.Producto;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Logica_Upload_Product {

    private Fragment uploadProduct;
    public static final  int GALLERY_REQUEST_CODE =101;
    public static final  int CAMER_REQUEST_CODE=102;
    private String cameraFilePath;
    private   LinearLayout linearLayoutPreciosTarifas;
    private EditText editTextPrecioProducto;
    private CheckBox tarifas;
    private EditText editTextTarifasPrducto;
    private View lineaTarifas;
    private LinearLayout mRootLinearLayout;
    private EditText editTextPrecioTarifas;
    private EditText precioTarifasPersonalizdas;
    private TextWatcher textWatcherTarifas;
    private   LinearLayout linearLayoutTarifas;
    private TextView seleccionarFoto;
    private TextView tomarFoto;
    private Button subirProducto;
    private EditText nombreProducto;
    private EditText editTextDescricionProducto;
    private ImageView imageView;
    private EditText editTextAtributos;


    private EditText getEditTextPrecioTarifas;


    private CheckBox checkBoxAtributos;
    private static final int ID_editTextPrecioTarifas=301;
    private static final int ID_editTextAtributos=301;

    public String getCameraFilePath() {
        return cameraFilePath;
    }



    public void setCameraFilePath(String cameraFilePath) {
        this.cameraFilePath = cameraFilePath;
    }

    public Logica_Upload_Product(Fragment uploadProduct) {
        this.uploadProduct = uploadProduct;
    }

    public Fragment getUploadProduct() {
        return uploadProduct;
    }

    public void setUploadProduct(Fragment uploadProduct) {
        this.uploadProduct = uploadProduct;
    }

    public void dexterPermission() {
        Dexter.withActivity(this.getUploadProduct().getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()){


                }
                if (report.isAnyPermissionPermanentlyDenied()){
                    showSettingsDialog();

                }
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).onSameThread().check();
    }



    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
  public void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getUploadProduct().getActivity());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    public void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", this.getUploadProduct().getActivity().getPackageName(), null);
        intent.setData(uri);
      this.getUploadProduct().getActivity().startActivityForResult(intent, 103);
    }

   public void captureFromCamera() {
        try {
            System.out.println("capture CAmera");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this.getUploadProduct().getContext(), BuildConfig.APPLICATION_ID + ".Recursos.FileProvider", createImageFile()));
            this.getUploadProduct().getActivity().startActivityForResult(intent, CAMER_REQUEST_CODE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        this.getUploadProduct().getActivity().startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }


    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //This is the directory in which the file will be created. This is the default location of Camera photos
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for using again
        cameraFilePath = "file://" + image.getAbsolutePath();
        return image;
    }




    public void onRemoveView(View view){
        LinearLayout linearLayout=view.findViewById(R.id.layout_tarifas);
        linearLayout.removeAllViews();
        linearLayoutPreciosTarifas.removeAllViews();
        editTextPrecioProducto.setVisibility(View.VISIBLE);



    }

    /**
     * Inicia los campos predefinidos en el layout
     * @param view
     */
    public void initData(View view ){
        seleccionarFoto=view.findViewById(R.id.tvSeleccionarFoto);
        imageView=(ImageView) view.findViewById(R.id.ivfotoProducto);
        tomarFoto=view.findViewById(R.id.tvtomar);
        tarifas=(CheckBox)view.findViewById(R.id.checkbtnTarifasProducto);
        editTextTarifasPrducto=(EditText)view.findViewById(R.id.etTarifasProducto);
        lineaTarifas=(View)view.findViewById(R.id.linea_tarifas);
        mRootLinearLayout = (LinearLayout) view.findViewById(R.id.rootLinearLayout);
        linearLayoutPreciosTarifas=(LinearLayout)view.findViewById(R.id.layout_precios_tarifas);
        editTextPrecioProducto=(EditText)view.findViewById(R.id.etPrecioProducto);
        checkBoxAtributos=(CheckBox)view.findViewById(R.id.checkbtnAtributosProducto);
        subirProducto=(Button)view.findViewById(R.id.btnSubirProducto);
        nombreProducto=view.findViewById(R.id.etNombreProducto);
        editTextDescricionProducto=view.findViewById(R.id.etDescripcionProducto);
        editTextDescricionProducto=view.findViewById(R.id.etVariosAtributosProducto);



        seleccionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pickFromGallery();


            }
        });

        tomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureFromCamera();
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

        checkBoxAtributos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {

                }else {

                }
            }
        });

        subirProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Producto  producto= new Producto();

                producto.setNombre(nombreProducto.getText().toString());
                producto.setDescripcion(editTextDescricionProducto.getText().toString());
                producto.setPrecio((Integer.parseInt(editTextPrecioProducto.getText().toString())));
                producto.setUrl_img(sendImageToDatabase());
                ArrayList<String> atributos = new ArrayList<>();
                atributos.add(editTextAtributos.getText().toString());
                producto.setAtributos(atributos);

            }
        });
    }


    private  String sendImageToDatabase(){
        Bitmap bitmap = BitmapFactory.decodeResource(uploadProduct.getResources(),Integer.parseInt(imageView.getDrawable().toString()));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
        byte [] byte_arr = stream.toByteArray();
        String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);

        return image_str;
    }



    /**
     * Define el TextWatcher para el edit text de tarifas
     * una una vez indicado el numero de tarifas
     * se creare con un ciclo for las linea de tarifas indicadas
     * @param view
     * @return
     */
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

                        editTextPrecioTarifas = new EditText(uploadProduct.getActivity());
                        editTextPrecioTarifas.setHint("Tarifa nr: " + (i+1));
                        editTextPrecioTarifas.setHintTextColor(Color.WHITE);
                        editTextPrecioTarifas.setTextColor(Color.WHITE);
                        linearLayoutPreciosTarifas.addView(editTextPrecioTarifas);


                        View linea = new View(uploadProduct.getActivity());
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


    /**
     * Para implementar luego
     * para los campos de tarifas añadidos dinamicamente
     * @param view
     * @return
     */
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


                int idTarifasPersonalizadas=ID_editTextPrecioTarifas+10;
                precioTarifasPersonalizdas = new EditText(uploadProduct.getActivity());
                precioTarifasPersonalizdas .setHint("Precio tarifa : " + editable.toString());
                precioTarifasPersonalizdas .setHintTextColor(Color.WHITE);
                precioTarifasPersonalizdas .setTextColor(Color.WHITE);
                linearLayoutPreciosTarifas.addView(  precioTarifasPersonalizdas );

                View linea = new View(uploadProduct.getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                linea.setLayoutParams(params);
                linea.setBackgroundColor(Color.WHITE);
                linearLayoutPreciosTarifas.addView(linea);


            }


        };
        return textWatcherTarifas;
    }


    /**
     *Añade la el campo para que inidques el nr de tarifas
     * y quita la posbilidad del precio unico
     * @param view
     */
    public void onAddFieldNrTarifas(View view) {
        editTextPrecioProducto.setVisibility(View.INVISIBLE);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layout_tarifas);
        editTextPrecioTarifas = new EditText(uploadProduct.getActivity());
        editTextPrecioTarifas.setId(ID_editTextPrecioTarifas);
        editTextPrecioTarifas.setHint("Indique el numero de tarifas");
        editTextPrecioTarifas.setHintTextColor(Color.WHITE);
        editTextPrecioTarifas.setTextColor(Color.WHITE);
        linearLayout.addView(editTextPrecioTarifas);

        View linea=new View(uploadProduct.getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        linea.setLayoutParams(params);
        linea.setBackgroundColor(Color.WHITE);
        linearLayout.addView(linea);

        editTextPrecioTarifas.addTextChangedListener(setTextChangeListeners(view));


    }




}
