package com.example.sample4location;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Location location = intent.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);
		if (location != null) {
			Toast.makeText(this, "single update : " + location.toString(), Toast.LENGTH_SHORT).show();
			return Service.START_NOT_STICKY;
		}
		int status = intent.getIntExtra(LocationManager.KEY_STATUS_CHANGED, -1);
		if (status != -1) {
			switch(status) {
			
			}
			return Service.START_NOT_STICKY;
		}
		boolean isEnabled = intent.getBooleanExtra(LocationManager.KEY_PROVIDER_ENABLED, false);
		// ...
		return Service.START_NOT_STICKY;
	}

}
