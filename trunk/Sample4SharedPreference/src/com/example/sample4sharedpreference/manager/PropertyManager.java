package com.example.sample4sharedpreference.manager;

import com.example.sample4sharedpreference.MyApplication;

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
	private static final String PREF_NAME = "myprefs";
	SharedPreferences.Editor mEditor;
	
	private PropertyManager() {
		mPrefs = MyApplication.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		mEditor = mPrefs.edit();
	}
	
	private static final String USER_ID = "userid";
	private String mUserId;
	
	public void setUserId(String userId) {
		mUserId = userId;
		mEditor.putString(USER_ID, userId);
		mEditor.commit();
	}
	
	public String getUserId() {
		if (mUserId == null) {
			mUserId = mPrefs.getString(USER_ID, "");
		}
		return mUserId;
	}
	
	private static final String PASSWORD = "password";
	private String mPassword;
	
	public void setPassword(String password) {
		mPassword = password;
		mEditor.putString(PASSWORD, password);
		mEditor.commit();
	}
	
	public String getPassword() {
		if (mPassword == null) {
			mPassword = mPrefs.getString(PASSWORD, "");
		}
		return mPassword;
	}
}
