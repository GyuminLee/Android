package org.tacademy.appwidget;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.RemoteViews;

public class ExampleAppWidgetProvider extends AppWidgetProvider {


	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
		PackageManager pm = context.getPackageManager();
		pm.setComponentEnabledSetting(new ComponentName("org.tacademy.appwidget",".ExampleAppWidgetProvider"), 
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
				PackageManager.DONT_KILL_APP);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		Log.i("ExampleAppWidgetProvider","onUpdate");
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		RemoteViews views;
		ComponentName watchWidget;
	
		Intent intent = new Intent(context,AppWidgetActivity.class);
		PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
		views = new RemoteViews(context.getPackageName(),R.layout.appwidgetlayout);
		views.setTextViewText(R.id.TextView01, "OnUpdate...");
		views.setOnClickPendingIntent(R.id.TextView01, pi);
	
		watchWidget = new ComponentName(context, ExampleAppWidgetProvider.class);
		appWidgetManager.updateAppWidget(watchWidget, views);
		
	}


}
