package com.example.samplelocationtest2;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.widget.Toast;

public class MyLocationService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Location location = intent.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);
		if (location != null) {
			Toast.makeText(this, 
					"Location lat : " + location.getLatitude() + ", lng : " + location.getLongitude(), 
					Toast.LENGTH_SHORT).show();
		}
		return Service.START_NOT_STICKY;
	}
}
