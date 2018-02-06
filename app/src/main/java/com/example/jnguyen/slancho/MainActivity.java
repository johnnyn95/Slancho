package com.example.jnguyen.slancho;


import android.content.Intent;


import android.os.AsyncTask;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

import java.net.URL;

import com.example.jnguyen.slancho.Data.SlanchoPreferences;
import com.example.jnguyen.slancho.Utilities.NetworkUtilities;
import com.example.jnguyen.slancho.Utilities.OpenWeatherJsonUtils;
import com.example.jnguyen.slancho.Utilities.SlanchoWeatherUtils;

//TODO Implement Settings/Preferences
//TODO Implement Share Function
//TODO Implement About
//TODO Create and Implement Adapter
//TODO Create Content Provider

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private SlanchoAdapter mAdapter;


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
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_forecast);
        mAdapter = new SlanchoAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);


        performAsyncTask();
    }

    private void performAsyncTask(){
        //URL weatherUrl =  NetworkUtilities.buildURLforFiveDays("sofia,bulgaria");
        String weatherData = SlanchoPreferences.getPreferredWeatherLocation(this);
        new queryTask().execute(weatherData);
    }


    public class queryTask extends AsyncTask<String,Void,String[]>{
        @Override
        protected String[] doInBackground(String... params) {
            if(params.length == 0 ){
                return null;
            }

            String result = params[0];
            URL weatherRequestedUrl = NetworkUtilities.buildURLforFiveDays(result);
            //URL weatherRequestedUrl = NetworkUtilities.returnDummyData();
            try {
                String jsonWeatherResponse = NetworkUtilities.getResponseFromHttpUrl(weatherRequestedUrl);
                String[] simpleJsonWeatherData = OpenWeatherJsonUtils.getSimpleWeatherStringsFromJson(MainActivity.this,jsonWeatherResponse);
                return simpleJsonWeatherData;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            super.onPostExecute(weatherData);
            mAdapter.setWeatherData(weatherData);
        }
    }
}
