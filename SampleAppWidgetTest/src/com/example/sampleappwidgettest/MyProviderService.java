package com.example.sampleappwidgettest;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class MyProviderService extends Service {

	int mCount = 0;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		AppWidgetManager awm = AppWidgetManager.getInstance(this);
		ComponentName cn = new ComponentName(this, MyAppWidgetProvider.class);
		
		RemoteViews views = new RemoteViews(getPackageName(), R.layout.app_widget_layout);
		views.setTextViewText(R.id.location, "location...." + mCount);
		mCount++;
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), 0);
		views.setOnClickPendingIntent(R.id.location, pi);
		
		awm.updateAppWidget(cn, views);
		
		return Service.START_NOT_STICKY;
	}

}
