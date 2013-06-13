package com.example.testappwidgetsample2;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				Intent intent = new Intent(MainActivity.this, MyService.class);
				PendingIntent pi = PendingIntent.getService(MainActivity.this, 0, intent, 0);
				am.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 10000, 5000, pi);		
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
