package com.example.samplelocationtest;

import android.app.Service;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		boolean enter = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
		String address = intent.getStringExtra("address");
		if (enter) {
			Toast.makeText(this, "enter..." + address, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "exit..." + address, Toast.LENGTH_SHORT).show();
		}
		return super.onStartCommand(intent, flags, startId);
	}
}
