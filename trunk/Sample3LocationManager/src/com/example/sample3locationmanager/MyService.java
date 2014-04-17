package com.example.sample3locationmanager;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Location location = intent
				.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);
		int status = intent.getIntExtra(LocationManager.KEY_STATUS_CHANGED, -1);
		if (location != null) {
			Toast.makeText(this,
					"single update location " + location.toString(),
					Toast.LENGTH_SHORT).show();
		} else if (status != -1) {
			switch(status) {
			case LocationProvider.AVAILABLE :
				Toast.makeText(this, "provider available", Toast.LENGTH_SHORT).show();
				break;
			case LocationProvider.OUT_OF_SERVICE :
				Toast.makeText(this, "provider out of service", Toast.LENGTH_SHORT).show();
				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE :
				Toast.makeText(this, "provider temporarily unavailable", Toast.LENGTH_SHORT).show();
				break;
			}
		} else {
			boolean enabled = intent.getBooleanExtra(LocationManager.KEY_PROVIDER_ENABLED, false);
			if (enabled) {
				Toast.makeText(this, "provider enabled", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "provider disabled", Toast.LENGTH_SHORT).show();
			}
		}
		return Service.START_NOT_STICKY;
	}

}
