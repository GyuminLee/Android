package com.example.sampleandroidstaggeredgridview;

import com.begentgroup.imageloader.Configuration;
import com.begentgroup.imageloader.ImageLoader;

import android.app.Application;
import android.graphics.Bitmap.CompressFormat;

public class MyApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		Configuration configuration = new Configuration(this, R.drawable.ic_empty, R.drawable.ic_stub, R.drawable.ic_error);
		ImageLoader.getInstance().initialize(configuration);
	}
}
