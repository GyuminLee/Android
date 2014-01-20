package com.example.sample2appwidget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class MyService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	int mCount = 0;
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		RemoteViews views = new RemoteViews(getPackageName(), R.layout.app_widget_layout);
		views.setTextViewText(R.id.titleView, "AppWidgetTitle");
		views.setTextViewText(R.id.descView, "descView : " + mCount++);
		Intent i = new Intent(this,MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
		
		views.setOnClickPendingIntent(R.id.descView, pi);
		
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		ComponentName provider = new ComponentName(this, MyAppWidgetProvider.class);
		manager.updateAppWidget(provider, views);
		
		return Service.START_NOT_STICKY;
	}
}
