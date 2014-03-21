package com.example.testnavermovies;

import com.begentgroup.imageloader.Configuration;
import com.begentgroup.imageloader.ImageLoader;

import android.app.Application;

public class MyApplication extends Application {

//	private static ImageLoader mImageLoader;
	
	@Override
	public void onCreate() {
		super.onCreate();
//		Volley.newRequestQueue(this);
		Configuration configuration = new Configuration(this, R.drawable.ic_empty, R.drawable.ic_stub, R.drawable.ic_error);
		ImageLoader.getInstance().initialize(configuration);
	}
	
}
