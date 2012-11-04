package com.magnitude.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * The SettingsActivity is used to manage global preferences of the application. For more information, check settings.xml resource file
 * @author Magnitude Client
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Magnitude Settings");
		//Loads preferences
		getPreferenceManager().setSharedPreferencesName("settings");
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
		addPreferencesFromResource(R.xml.settings);
		
	}
	
	@Override
    protected void onDestroy() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }


	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		//If the preferences changed, save !
		getPreferenceManager().getSharedPreferences().edit().commit();
	}

}
