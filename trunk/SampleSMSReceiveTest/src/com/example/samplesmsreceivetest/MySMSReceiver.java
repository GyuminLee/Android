package com.example.samplesmsreceivetest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MySMSReceiver extends BroadcastReceiver {

	private final static String TAG = "MySMSReceiver";
	
	public static int mId = 0;
	
	@Override
	public void onReceive(Context context, Intent intent) {
	    // TODO Auto-generated method stub
//		Toast.makeText(context, "sms received", Toast.LENGTH_SHORT).show();
		Log.i(TAG, "sms received");
//		Intent serviceIntent = new Intent(context, MyService.class);
//		context.startService(intent);
//		Intent activityIntent = new Intent(context, MainActivity.class);
//		activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		context.startActivity(activityIntent);
		
		NotificationManager nm = 
				(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent actionIntent = new Intent(context,MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(context, 0, actionIntent, 0);

		
//		Notification noti = new Notification.Builder(context)
//        .setContentTitle("title")
//        .setContentText("message")
//        .setSmallIcon(R.drawable.ic_launcher)
//        .setAutoCancel(true)
//        .setContentIntent(pi)
//        .setContentInfo("info")
//        .build();

		
		Notification noti = new Notification(R.drawable.ic_launcher, "sms received...", System.currentTimeMillis());
		noti.setLatestEventInfo(context, "title", "message", pi);
		
		nm.notify(mId++, noti);
		
	}

}
