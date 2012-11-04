package com.magnitude.app;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

	private static Context mApplicationContext;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mApplicationContext = this;
	}
	
	public static Context getContext() {
		return mApplicationContext;
	}
	
}
