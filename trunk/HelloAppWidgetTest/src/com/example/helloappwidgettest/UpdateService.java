package com.example.helloappwidgettest;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.RemoteViews;

public class UpdateService extends Service {

	Handler mHandler = new Handler();
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mHandler.post(updateRunnable);
	}

	int interval = 30000;
	
	Runnable updateRunnable = new Runnable() {
		@Override
		public void run() {
			AppWidgetManager awm = AppWidgetManager.getInstance(UpdateService.this);
			ComponentName comp = new ComponentName(UpdateService.this, MyAppWidgetProvider.class);
			
			RemoteViews views = new RemoteViews(UpdateService.this.getPackageName(),R.layout.app_widget_layout);
			views.setTextViewText(R.id.title, "my app widget");
			views.setTextViewText(R.id.message, "time : " + System.currentTimeMillis());
			
			awm.updateAppWidget(comp, views);
			
			mHandler.postDelayed(this, interval);
		}
	};
	
	@Override
	public void onDestroy() {
		mHandler.removeCallbacks(updateRunnable);
		super.onDestroy();
	}
}
