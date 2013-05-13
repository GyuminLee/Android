package com.example.samplelocationtest2;

import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.widget.Toast;

public class MyAlertService extends Service {

	public static final String PARAM_ALERT_LOCATION = "location";
	public static final String PARAM_ALERT_ADDRESS = "address";
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		boolean isEntering = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
		Location location = (Location)intent.getParcelableExtra(PARAM_ALERT_LOCATION);
		if (location != null) {
			Address address = (Address)intent.getParcelableExtra(PARAM_ALERT_ADDRESS);
			if (isEntering) {
				Toast.makeText(this, "entering alert...", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "outering alert...", Toast.LENGTH_SHORT).show();
			}
		} 
		return Service.START_NOT_STICKY;
	}
}
