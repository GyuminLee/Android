package com.example.sampledatabasetest;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

	private static Context mContext;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		mContext = this;
	}
	
	public static Context getContext() {
		return mContext;
	}
}
