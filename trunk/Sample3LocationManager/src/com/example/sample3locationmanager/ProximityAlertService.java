package com.example.sample3locationmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.LocationManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class ProximityAlertService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	NotificationManager mNM;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mNM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
	}
	private static int mId = 0;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		boolean isEnter = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
		Address address = intent.getParcelableExtra("address");
		Uri uri = intent.getData();		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setTicker("Proximity Alert");
		builder.setContentTitle("Location");
		builder.setContentText("enter " + address.getLocality());
		builder.setWhen(System.currentTimeMillis());
		Intent i = new Intent(this, NotifyActivity.class);
		i.putExtra("address", address);
		if (isEnter) {
			Toast.makeText(this, "proximity enter", Toast.LENGTH_SHORT).show();
			i.putExtra("isEnter", true);
		} else {
			Toast.makeText(this, "proximity exit", Toast.LENGTH_SHORT).show();
			i.putExtra("isEnter", false);
		}
		PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
		builder.setContentIntent(pi);
		builder.setAutoCancel(true);
		builder.setDefaults(Notification.DEFAULT_ALL);
		mNM.notify(mId++, builder.build());
		return Service.START_NOT_STICKY;
	}
}
