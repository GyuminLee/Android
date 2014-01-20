package com.example.sample2alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	AlarmManager mAM;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mAM = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		Button btn = (Button)findViewById(R.id.btnAlarm);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, AlarmActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.setFlags(Intent.FLAG_FROM_BACKGROUND);
				PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, i, 0);
				
				long time = System.currentTimeMillis() + 10 * 1000;
				
				mAM.set(AlarmManager.RTC_WAKEUP, time, pi);
				
				
				
			}
		});
		
		btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, AlarmActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.setFlags(Intent.FLAG_FROM_BACKGROUND);
				PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, i, 0);
				mAM.cancel(pi);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
