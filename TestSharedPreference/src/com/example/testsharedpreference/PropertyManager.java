package com.example.testsharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

public class PropertyManager {
	
	SharedPreferences prefs;
	SharedPreferences.Editor editor;
	
	static final String PREF_NAME="my_pref";
	static final int UNLOAD_INT = -1;
	
	static final String PREF_PARAM_ID = "userid";
	static final String PREF_PARAM_PASSWORD = "password";
	static final String PREF_PARAM_AGE = "age";
	
	private String userid;
	private String password;
	private int age = UNLOAD_INT;
	private User user;
	
	private static PropertyManager instance;
	public static PropertyManager getInstance() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}
	
	private PropertyManager() {
		Context context = MyApplication.getContext();
		prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		editor = prefs.edit();
	}
	
	public void setUserId(String id) {
		userid = id;
		editor.putString(PREF_PARAM_ID, id);
		editor.commit();
	}
	
	public String getUserId() {
		if (userid == null) {
			userid = prefs.getString(PREF_PARAM_ID, "");
		}
		return userid;
	}
	
	public void setUserPassword(String passwd) {
		password = passwd;
		editor.putString(PREF_PARAM_PASSWORD, passwd);
		editor.commit();
	}
	
	public String getUserPassword() {
		if (password == null) {
			password = prefs.getString(PREF_PARAM_PASSWORD, "");
		}
		return password;
	}
	
	public void setUserAge(int age) {
		this.age = age;
		editor.putInt(PREF_PARAM_AGE, age);
		editor.commit();
	}
	
	public int getUserAge() {
		if (age == UNLOAD_INT) {
			age = prefs.getInt(PREF_PARAM_AGE, 0);
		}
		return age;
	}
	
	public void setUser(User user) {
		this.user = user;
		editor.putString(PREF_PARAM_ID, user.id);
		editor.putString(PREF_PARAM_PASSWORD, user.pw);
		editor.putInt(PREF_PARAM_AGE, user.age);
		editor.commit();
	}
	
	public User getUser() {
		if (user == null) {
			user = new User();
			user.id = prefs.getString(PREF_PARAM_ID, "");
			user.pw = prefs.getString(PREF_PARAM_PASSWORD, "");
			user.age = prefs.getInt(PREF_PARAM_AGE, 0);
		}
		return user;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
