package com.example.sample4location;

import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.LocationManager;
import android.os.IBinder;
import android.widget.Toast;

public class AlertService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		boolean isEnter = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
		Address addr = intent.getParcelableExtra("address");
		if (isEnter) {
			Toast.makeText(this, "enter...", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "exit...", Toast.LENGTH_SHORT).show();			
		}
		return Service.START_NOT_STICKY;
	}

}
