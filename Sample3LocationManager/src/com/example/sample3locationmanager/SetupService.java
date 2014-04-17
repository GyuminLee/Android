package com.example.sample3locationmanager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;

public class SetupService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	LocationManager mLM;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// ..
		return Service.START_NOT_STICKY;
	}
}
