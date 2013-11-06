package com.example.hellolbstest;

import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.LocationManager;
import android.os.IBinder;
import android.widget.Toast;

public class AlertService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Address address = intent.getParcelableExtra("address");
		boolean isEnter = intent.getBooleanExtra(
				LocationManager.KEY_PROXIMITY_ENTERING, false);
		if (isEnter) {
			Toast.makeText(this, "entering ... " + address.toString(),
					Toast.LENGTH_SHORT).show();
		}

		return Service.START_NOT_STICKY;
	}

}
