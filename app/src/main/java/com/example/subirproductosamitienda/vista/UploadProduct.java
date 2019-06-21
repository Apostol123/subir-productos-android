package com.example.subirproductosamitienda.vista;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subirproductosamitienda.BuildConfig;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadProduct extends Fragment {

        private TextView seleccionarFoto;
        private TextView tomarFoto;
        private ImageView imageView;
        private String cameraFilePath;
        private static final  int GALLERY_REQUEST_CODE =101;
    private static final  int CAMER_REQUEST_CODE=102;
    private Bitmap mImageBitmap;

    public UploadProduct() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_upload_product, container, false);
       initData(view);
       dexterPermission();

        return  view;
    }

    private void initData(View view) {
            seleccionarFoto=view.findViewById(R.id.tvSeleccionarFoto);
            imageView=(ImageView) view.findViewById(R.id.ivfotoProducto);
            tomarFoto=view.findViewById(R.id.tvtomar);

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
                        mImageBitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), Uri.parse(cameraFilePath));
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

    public void dexterPermission() {
        Dexter.withActivity(this.getActivity())
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
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
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
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", this.getActivity().getPackageName(), null);
        intent.setData(uri);
        this.getActivity().startActivityForResult(intent, 101);
    }

    private void captureFromCamera() {
        try {
            System.out.println("capture CAmera");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this.getContext(), BuildConfig.APPLICATION_ID + ".Recursos.FileProvider", createImageFile()));
            this.getActivity().startActivityForResult(intent, CAMER_REQUEST_CODE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        this.getActivity().startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }


    private File createImageFile() throws IOException {
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







}
