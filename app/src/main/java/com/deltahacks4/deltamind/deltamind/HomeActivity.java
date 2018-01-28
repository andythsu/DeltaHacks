package com.deltahacks4.deltamind.deltamind;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 100;
    private Button openButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.openButton = findViewById(R.id.openButton);

        this.registerListeners();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                } else {
                    this.openButton.setClickable(false);
                }
        }
    }


    private void registerListeners() {
        this.openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                // Check if the user has granted permission to use camera, if not, ask for permission.
                if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[] {Manifest.permission.CAMERA}, 100);
                } else {
                    takePicture();
                }*/
                Intent newIntent = new Intent(view.getContext(), DetailActivity.class);
                startActivity(newIntent);
            }
        });
    }


    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File Object to store the picture taken
            File pictureFile = null;

            try {
                pictureFile = configImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (pictureFile != null) {
                Uri pictureURI = FileProvider.getUriForFile(this, "com.deltahacks4.deltamind.deltamind.fileprovider", pictureFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    private File configImageFile() throws IOException {
        String photoPath = getPackageName();

        try {
            photoPath = getPackageManager().getPackageInfo(photoPath, 0).applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // TODO: Change file name when integrating with View
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg"
                //storageDir
        );

        return image;
    }
}
