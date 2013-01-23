package com.example.testappwidgetsample;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class MyService extends Service implements Runnable {

	int count = 0;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	boolean isRunning = true;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		new Thread(this).start();
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isRunning) {
			try {
				Thread.sleep(10 * 1000);
				
				updateAppWidget();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		isRunning = false;
		super.onDestroy();
	}
	
	public void updateAppWidget() {
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		ComponentName provider = new ComponentName(this, MyAppWedgetProvider.class);
		
		RemoteViews views = new RemoteViews(getPackageName(), R.layout.appwidget_layout);
		
		views.setTextViewText(R.id.textView1, "count : " + count);
		
		manager.updateAppWidget(provider, views);
		
	}
}
