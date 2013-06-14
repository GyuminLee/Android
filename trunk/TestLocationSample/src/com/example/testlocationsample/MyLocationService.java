package com.example.testlocationsample;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.widget.Toast;

public class MyLocationService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Location location = intent.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);
		if (location != null) {
			Toast.makeText(this, "receive location", Toast.LENGTH_SHORT).show();
		}
		return Service.START_NOT_STICKY;
	}

}
