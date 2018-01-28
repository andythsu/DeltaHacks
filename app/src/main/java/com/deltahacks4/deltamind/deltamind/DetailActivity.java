package com.deltahacks4.deltamind.deltamind;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class DetailActivity extends AppCompatActivity {

    String fileName;
    String rmd_title;
    String pic_absolute_path;

    TextView titleLabel;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.titleLabel = findViewById(R.id.titleLabel);
        this.imageView = findViewById(R.id.imageView);

        onNewIntent(getIntent());
        Toast.makeText(this, fileName, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onNewIntent(Intent intent){
        fileName = intent.getStringExtra("fileName");
        rmd_title = intent.getStringExtra("title");
        pic_absolute_path = intent.getStringExtra("pic_absolute_path");

        this.titleLabel.setText(this.titleLabel.getText() + " " + rmd_title);

        File imgFile = new File(pic_absolute_path);

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            this.imageView.setImageBitmap(myBitmap);
        }
    }
}
