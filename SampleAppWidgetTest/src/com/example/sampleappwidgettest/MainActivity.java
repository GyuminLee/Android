package com.example.sampleappwidgettest;

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
				// TODO Auto-generated method stub
				AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				Intent i = new Intent(MainActivity.this, MyProviderService.class);
				PendingIntent pi = PendingIntent.getService(MainActivity.this, 0, i, 0);
				am.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 10 * 1000, 60 * 1000, pi);
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
