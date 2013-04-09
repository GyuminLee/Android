package com.example.sampledatabasetest.manager;

import com.example.sampledatabasetest.MyApplication;

import android.content.Context;
import android.content.SharedPreferences;

public class PropertyManager {

	SharedPreferences prefs;
	SharedPreferences.Editor editor;
	private final static String PREF_NAME = "mysetting";
	
	
	public static final String KEY_USER_NAME = "username";
	private String userName;
	
	
	private static PropertyManager instance;
	public static PropertyManager getInstance() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}
	
	private PropertyManager() {
		prefs = MyApplication.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		editor = prefs.edit();
	}
	
	public String getUserName() {
		if (userName == null) {
			userName = prefs.getString(KEY_USER_NAME, "");
		}
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
		editor.putString(KEY_USER_NAME, userName);
		editor.commit();
	}
	
}
