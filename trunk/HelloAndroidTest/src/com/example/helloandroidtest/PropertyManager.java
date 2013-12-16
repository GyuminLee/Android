package com.example.helloandroidtest;

import android.content.SharedPreferences;

public class PropertyManager {
	SharedPreferences prefs;
	
	private static PropertyManager instance;
	public static PropertyManager getInstance() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}
	
	private PropertyManager() {
		prefs = MyApplication.getContext().getSharedPreferences("MY_PREFS", 0);
	}
}
