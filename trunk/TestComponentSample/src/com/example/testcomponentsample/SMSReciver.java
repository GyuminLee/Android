package com.example.testcomponentsample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class SMSReciver extends BroadcastReceiver {

	private static final String TAG = "SMSReceiver";
	
	private static int mId = 1;
	
	@Override
	public void onReceive(Context context, Intent intent) {
//		Toast.makeText(context, "SMS Received", Toast.LENGTH_SHORT).show();
		Log.i(TAG, "SMSREceiver received");
		NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setTicker("SMS Receive...");
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentTitle("SMS");
		builder.setContentText("ContentText");
		builder.setContentInfo("info");
		builder.setWhen(System.currentTimeMillis());
		builder.setAutoCancel(true);
		builder.setDefaults(Notification.DEFAULT_ALL);
		Intent i = new Intent(context, SMSActivity.class);
		PendingIntent pi =  PendingIntent.getActivity(context, 0, i, 0);
		builder.setContentIntent(pi);
		
		Intent ci = new Intent(context, SMSActivity.class);
		PendingIntent cpi = PendingIntent.getActivity(context, 0, ci, 0);
		builder.setDeleteIntent(cpi);
		
		Notification nofication = builder.build();
		nm.notify(mId++, nofication);
	}

}
