package com.example.testfilesample;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

	private static Context mContext;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		mContext = this;
		super.onCreate();
	}
	
	public static Context getContext() {
		return mContext;
	}
}
