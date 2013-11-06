package com.example.hellolbstest;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Location location = intent.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);
		String poi = intent.getStringExtra("poi");
		Toast.makeText(this, "location changed...", Toast.LENGTH_SHORT).show();
		return Service.START_NOT_STICKY;
	}

}
