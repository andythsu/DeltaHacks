package com.deltahacks4.deltamind.deltamind;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class DetailActivity extends AppCompatActivity {

    private ImageSwitcher imageSwitcher;
    private Button previousButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.imageSwitcher = findViewById(R.id.imageSwitcher);
        this.previousButton = findViewById(R.id.previousButton);
        this.nextButton = findViewById(R.id.nextButton);

        this.imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView newView = new ImageView(getApplicationContext());
                newView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                newView.setLayoutParams(new ImageSwitcher.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT
                ));
                return newView;
            }
        });

        this.previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSwitcher.setImageResource(R.drawable.ic_launcher_foreground);
            }
        });

        this.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSwitcher.setImageResource(R.drawable.ic_launcher_background);
            }
        });
    }
}
