package com.example.jnguyen.slancho;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView mDescription = (TextView) findViewById(R.id.about_description);
        String aboutDescription = getResources().getString( R.string.about_description);
        mDescription.setText(aboutDescription);
    }

    private  void openLinkedInProfile(View v){
        String linkedInLink = getResources().getString( R.string.linkedInLink);
        Uri uri = Uri.parse(linkedInLink);
        Intent webpageIntent = new Intent(Intent.ACTION_VIEW,uri);

        if (webpageIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(webpageIntent);
        }
    }
}
