package com.example.samplelocationtest2;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
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
			LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			Intent i = new Intent(this,MyAlertService.class);
			i.putExtra(MyAlertService.PARAM_ALERT_LOCATION, location);
			PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
			locationManager.addProximityAlert(location.getLatitude(), location.getLongitude(), 50, -1, pi);
		}
		return Service.START_NOT_STICKY;
	}
}
