package com.example.subirproductosamitienda.Logica;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.fragment.app.Fragment;

import com.example.subirproductosamitienda.BuildConfig;
import com.example.subirproductosamitienda.Recursos.FileProvider;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Logica_Upload_Product {

    private Fragment uploadProduct;
    public static final  int GALLERY_REQUEST_CODE =101;
    public static final  int CAMER_REQUEST_CODE=102;
    private String cameraFilePath;

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





}
