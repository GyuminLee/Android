package com.example.testlocationsample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class NotificationService extends Service {

	public static final String PARAM_NAME = "name";
	public static final String PARAM_LOCATION = "location";
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		boolean isEntering = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
		if (isEntering) {
			String name = intent.getStringExtra(PARAM_NAME);
			String location = intent.getStringExtra(PARAM_LOCATION);
			
			NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
			NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
			builder.setSmallIcon(R.drawable.ic_launcher);
			builder.setTicker("proximity alert " + name);
			builder.setAutoCancel(true);
			builder.setWhen(System.currentTimeMillis());
			builder.setContentTitle(name);
			builder.setContentText(location);
			Intent i = new Intent(this, MainActivity.class);
			PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
			builder.setContentIntent(pi);
			nm.notify(1, builder.build());
		}
		return Service.START_NOT_STICKY;
	}

}
