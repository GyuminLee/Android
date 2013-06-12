package com.example.testfilesample;

import android.content.Context;
import android.content.SharedPreferences;

public class PropertyManager {

	public static final String PREF_NAME = "myprefs";
	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;
	
	private static final String FIELD_USERNAME = "username";
	private static String mUserName = null;
	
	private static final String FIELD_PASSWORD = "password";
	private static String mPassword = null;
	
	private static PropertyManager instance;
	public static PropertyManager getInstance() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}
	
	private PropertyManager() {
		prefs = MyApplication.getContext().getSharedPreferences(PREF_NAME, 0);
		editor = prefs.edit();
	}
	
	public void setUserName(String userName) {
		mUserName = userName;
		editor.putString(FIELD_USERNAME, userName);
		editor.commit();
	}
	
	public String getUserName() {
		if (mUserName == null) {
			mUserName = prefs.getString(FIELD_USERNAME, "");
		}
		return mUserName;
	}
	
	public void setPassword(String password) {
		mPassword = password;
		editor.putString(FIELD_PASSWORD, password);
		editor.commit();
	}
	
	public String getPassword() {
		if (mPassword == null) {
			mPassword = prefs.getString(FIELD_PASSWORD, "");
		}
		return mPassword;
	}
}
