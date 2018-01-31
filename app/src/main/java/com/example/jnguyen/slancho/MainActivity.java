package com.example.jnguyen.slancho;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jnguyen.slancho.Utilities.NetworkUtilities;

import java.net.URL;
//TODO Implement Settings/Preferences
//TODO Implement Share Function
//TODO Implement About
//TODO Create and Implement Adapter
//TODO Create Content Provider

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
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
                //TODO Implement/Decide what type of data to share
                String mimeType = "text/plain";
                String title = "Weather shared from Slancho App!";
                String textToShare = "Today the weather will be Sunny, MAX TEMP:10 MIN TEMP:3";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO test network utilities
        TextView test = (TextView) findViewById(R.id.tv_test);
        URL testUrl = NetworkUtilities.buildURLforFiveDays("Sofia");


    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
