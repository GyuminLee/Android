package com.example.sample4appwidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class WidgetUpdateService extends Service {

	public static final String PARAM_MESSAGE = "message";
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	AppWidgetManager appWidgetManager;
	@Override
	public void onCreate() {
		super.onCreate();
		appWidgetManager = AppWidgetManager.getInstance(this);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String message = intent.getStringExtra(PARAM_MESSAGE);
		RemoteViews views = new RemoteViews(getPackageName(), R.layout.appwidget_layout);
		views.setTextViewText(R.id.textView1, message);
		ComponentName provider = new ComponentName(this, MyAppWidgetProvider.class);
		appWidgetManager.updateAppWidget(provider, views);
		return Service.START_NOT_STICKY;
	}

}
