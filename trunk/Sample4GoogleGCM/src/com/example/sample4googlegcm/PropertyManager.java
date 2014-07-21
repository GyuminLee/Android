package com.example.sample4googlegcm;

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
	SharedPreferences.Editor mEditor;
	public static final String PERFS_NAME = "myprefs";
	
	private PropertyManager() {
		mPrefs = MyApplication.getContext().getSharedPreferences(PERFS_NAME, Context.MODE_PRIVATE);
		mEditor = mPrefs.edit();
	}
	
	private static final String FIELD_REG_ID = "regid";
	private String regId;
	
	public void setRegistrationId(String regId) {
		this.regId = regId;
		mEditor.putString(FIELD_REG_ID, regId);
		mEditor.commit();
	}
	
	public String getRegistrationId() {
		if (regId == null) {
			regId = mPrefs.getString(FIELD_REG_ID, "");
		}
		return regId;
	}
	
}
