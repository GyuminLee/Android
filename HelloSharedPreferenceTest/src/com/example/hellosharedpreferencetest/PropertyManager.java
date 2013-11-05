package com.example.hellosharedpreferencetest;

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
	private static final String PREF_NAME = "myPref";
	
	SharedPreferences mPrefs;
	SharedPreferences.Editor mEditor;
	
	private PropertyManager() {
		Context context = MyApplication.getContext();
		mPrefs = context.getSharedPreferences(PREF_NAME, 0);
		mEditor = mPrefs.edit();
	}
	
	private final static String FIELD_USERNAME = "username";
	private String userName;
	private final static String FIELD_PASSWORD = "userpasswd";
	private String userPassword;
	
	public void setUserName(String userName) {
		this.userName = userName;
		mEditor.putString(FIELD_USERNAME, userName);
		mEditor.commit();
	}
	
	public String getUserName() {
		if (userName == null) {
			userName = mPrefs.getString(FIELD_USERNAME, "");
		}
		return userName;
	}
	
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
		mEditor.putString(FIELD_PASSWORD, userPassword);
		mEditor.commit();
	}
	
	public String getUserPassword() {
		if (userPassword == null) {
			userPassword = mPrefs.getString(FIELD_PASSWORD, "");
		}
		return userPassword;
	}
}
