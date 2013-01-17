package com.example.testdatabasesample;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

	static Context context;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		context = this;
	}
	
	public static Context getContext() {
		return context;
	}
}
