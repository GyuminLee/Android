package com.example.samplelocationmanager;

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

public class ProximityService extends Service {

	public static final String PARAM_ADDRESS = "addr";
	public static int sId = 0;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Address addr = intent.getParcelableExtra(PARAM_ADDRESS);
		if (addr != null) {
			boolean isEntering = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
			String message;
			if (isEntering) {
				// ...
				message = "proximity enter";
			} else {
				// ...
				message = "proximity exit";
			}
			
			NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
			
			NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
			
			builder.setAutoCancel(true);
			builder.setDefaults(Notification.DEFAULT_ALL);
			builder.setTicker(message);
			builder.setSmallIcon(R.drawable.ic_launcher);
			builder.setContentTitle("proximity alert");
			builder.setContentText(addr.toString());
			
			Intent i = new Intent(this, NotificationActivity.class);
			i.putExtra(PARAM_ADDRESS, addr);
			PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
			
			builder.setContentIntent(pi);
			
			
			nm.notify(sId++, builder.build());
			
			
		}
		
		return Service.START_NOT_STICKY;
	}
}
