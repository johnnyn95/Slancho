package com.example.jnguyen.slancho;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

/**
 * Created by JNguyen on 12.2.2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private void setPreferenceSummary(Preference preference,Object preferenceValue){
        String value = preferenceValue.toString();
        String key = preference.getKey();

        if(preference instanceof ListPreference){

            ListPreference listPreference = (ListPreference) preference;
            int preferenceIndex = listPreference.findIndexOfValue(value);

            if(preferenceIndex >= 0 ){
                listPreference.setSummary(listPreference.getEntries()[preferenceIndex]);
            }
        }
        else {
            preference.setSummary(value);
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_general);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int numberOfPreferences = preferenceScreen.getPreferenceCount();

        for (int i = 0 ;i < numberOfPreferences; i++){
            Preference preference = preferenceScreen.getPreference(i);

            if(!(preference instanceof CheckBoxPreference)){
                String preferenceValue = sharedPreferences.getString(preference.getKey(),"");
                setPreferenceSummary(preference,preferenceValue);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if(preference != null){
            if(!(preference instanceof CheckBoxPreference)){
                setPreferenceSummary(preference,sharedPreferences.getString(key,""));
            }
        }
    }
}
