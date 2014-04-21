package com.example.sample3appwidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.RemoteViews;

public class MyService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	Handler mHandler = new Handler();
	
	AppWidgetManager awm;
	@Override
	public void onCreate() {
		super.onCreate();
		awm = AppWidgetManager.getInstance(this);
	}
	
	boolean isStarted = false;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (!isStarted) {
			isStarted = true;
			isRunning = true;
			mHandler.post(appWidgetUpdate);
		}
		return Service.START_NOT_STICKY;
	}
	
	@Override
	public void onDestroy() {
		isRunning = false;
		super.onDestroy();
	}
	
	int count = 0;
	
	boolean isRunning = false;
	Runnable appWidgetUpdate = new Runnable() {
		
		@Override
		public void run() {
			if (isRunning) {
				RemoteViews views = new RemoteViews(MyService.this.getPackageName(), R.layout.app_widget_layout);
				views.setTextViewText(R.id.textView1, "count : " + count);
				count++;
				ComponentName provider = new ComponentName(MyService.this, MyAppWidgetProvider.class);
				awm.updateAppWidget(provider, views);
				mHandler.postDelayed(this, 20000);
			}
		}
	};
	
	

}
