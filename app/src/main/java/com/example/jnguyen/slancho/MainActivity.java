package com.example.jnguyen.slancho;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;

import com.example.jnguyen.slancho.Data.SlanchoPreferences;
import com.example.jnguyen.slancho.Utilities.NetworkUtilities;
import com.example.jnguyen.slancho.Utilities.OpenWeatherJsonUtils;

//TODO Implement Settings/Preferences
//TODO Implement Share Function
//TODO Implement About
//TODO Create and Implement Adapter
//TODO Create Content Provider

public class MainActivity extends AppCompatActivity implements
        SlanchoAdapter.SlanchoAdapterOnClickHandler,
        LoaderCallbacks<String[]>   {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private SlanchoAdapter mAdapter;


    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;

    private static final int OPENWEATHER_LOADER_ID = 95;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_forecast);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mErrorMessageDisplay =  findViewById(R.id.tv_error_message_display);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new SlanchoAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        int loaderId = OPENWEATHER_LOADER_ID;

        LoaderCallbacks<String[]> callback = MainActivity.this;
        Bundle bundleForLoader = null;

        getSupportLoaderManager().initLoader(loaderId,bundleForLoader,callback);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public Loader<String[]> onCreateLoader(int id,final Bundle loaderArgs) {
        return new AsyncTaskLoader<String[]>(this) {
            String[] mWeatherData = null;

            @Override
            protected void onStartLoading() {
                if(mWeatherData != null){
                    deliverResult(mWeatherData);
                } else {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public String[] loadInBackground() {
                String prefferedLocation = SlanchoPreferences.getPreferredWeatherLocation(MainActivity.this);

                URL weatherUrl = NetworkUtilities.buildUrl(prefferedLocation);
                try {
                    String jsonWeatherResponse = NetworkUtilities.getResponseFromHttpUrl(weatherUrl);
                    String[] jsonData = OpenWeatherJsonUtils
                            .getSimpleWeatherStringsFromJson(MainActivity.this,jsonWeatherResponse);

                    return jsonData;
                } catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(String[] data) {
                mWeatherData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mAdapter.setWeatherData(data);
        if (null == data) {
            showErrorMessage();
        } else {
            showWeatherDataView();
        }
    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {

    }

    private void invalidateData() {

        mAdapter.setWeatherData(null);
    }

    @Override
    public void onClick(String weather) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intent = new Intent(context,destinationClass);
        intent.putExtra(Intent.EXTRA_TEXT,weather);
        startActivity(intent);
    }

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

            case R.id.action_about :
                Intent aboutIntent = new Intent(this,AboutActivity.class);
                startActivity(aboutIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showWeatherDataView() {

        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}
