package com.example.sample2sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

public class PropertyManager {
	private static PropertyManager instance;
	public static PropertyManager getInstance() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}
	SharedPreferences mPrefs;
	private static final String PREF_NAME = "prefs";
	SharedPreferences.Editor mEditor;
	
	private PropertyManager() {
		mPrefs = MyApplication.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		mEditor = mPrefs.edit();
	}
	
	private final static String FIELD_NAME = "name";
	private String mName = null;
	
	public String getName() {
		if (mName == null) {
			mName = mPrefs.getString(FIELD_NAME, "");
		}
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
		mEditor.putString(FIELD_NAME, name);
		mEditor.commit();
	}
	
	private final static String FIELD_PASSWORD = "password";
	private String mPassword = null;
	
	public String getPassword() {
		if (mPassword == null) {
			mPassword = mPrefs.getString(FIELD_PASSWORD, "");
		}
		return mPassword;
	}
	
	public void setPassword(String password) {
		mPassword = password;
		mEditor.putString(FIELD_PASSWORD, password);
		mEditor.commit();
	}
	
}
