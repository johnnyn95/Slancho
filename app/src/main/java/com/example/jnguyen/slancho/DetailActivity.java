package com.example.jnguyen.slancho;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private String intentExtra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView weatherDetails = findViewById(R.id.tv_weather_details);

        Intent calledFromIntent = getIntent();
        intentExtra = calledFromIntent.getStringExtra(Intent.EXTRA_TEXT);
        if(intentExtra != "" && !equals("")){
            weatherDetails.setText(intentExtra);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClickedId = item.getItemId();
        switch (itemClickedId){

            case R.id.action_settings :
                Intent settingsIntent = new Intent(this,SettingsActivity.class);
                startActivity(settingsIntent);
                return true;

            case R.id.action_share :
                String mimeType = "text/plain";
                String title = "Weather shared from Slancho App!";
                String textToShare = intentExtra;

                ShareCompat.IntentBuilder.from(this)
                        .setType(mimeType)
                        .setChooserTitle(title)
                        .setText(textToShare)
                        .startChooser();
                return true;

            case R.id.action_about :
                Intent aboutIntent = new Intent(this,AboutActivity.class);
                startActivity(aboutIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
