package com.example.sample2location;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class AlertService extends Service {

	public static final String PARAM_ADDRESS = "address";

	int mId = 0;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		boolean isEntering = intent.getBooleanExtra(
				LocationManager.KEY_PROXIMITY_ENTERING, false);
		Address address = intent.getParcelableExtra(PARAM_ADDRESS);
		String message;
		if (isEntering) {
			message = address.getLocality() + " entered";
		} else {
			message = address.getLocality() + " exit";
		}
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		
		builder.setTicker(message);
		builder.setWhen(System.currentTimeMillis());
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentTitle("proximity alert");
		builder.setContentText(message);
		
		Intent i = new Intent(this,AlertActivity.class);
		i.putExtra("address", address);
		PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
		builder.setContentIntent(pi);
		
		builder.setAutoCancel(true);
		builder.setDefaults(Notification.DEFAULT_ALL);
		
		Notification notification = builder.build();
		nm.notify(mId++, notification);
		
		return Service.START_NOT_STICKY;
	}
}
