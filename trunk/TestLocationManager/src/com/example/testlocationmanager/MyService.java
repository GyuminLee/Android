package com.example.testlocationmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.IBinder;

public class MyService extends Service {

	public static final String ACTION_ALERT = "com.example.testlocationmanager.ACTION_PROXIMITY_ALERT";
	
	public static final String PARAM_ENTER = "entering";

	public static final String PARAM_NOTI_ID = "noti_id";
	
	public static int id = 0;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Uri data = intent.getData();
		boolean isEnter = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
		Intent i = new Intent(this, AlertActivity.class);
		i.setData(data);

		i.putExtra(PARAM_ENTER, isEnter);

		i.putExtra(PARAM_NOTI_ID, id);
		
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, i, 0);
		
		Notification  notification = new Notification(R.drawable.ic_launcher, "ticker text", System.currentTimeMillis());
		notification.setLatestEventInfo(this, "location alert", " .... ", contentIntent);
		
		// notification.sound
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		notification.vibrate = new long[] { 400, 200, 600, 200 };
		
		NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		
		nm.notify(id, notification);
		
		id++;
		
		return super.onStartCommand(intent, flags, startId);
	}

}
