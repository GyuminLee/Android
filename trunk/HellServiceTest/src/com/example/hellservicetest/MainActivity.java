package com.example.hellservicetest;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	ComponentName mComponentName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.btnStart);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MyService.class);
				i.putExtra("param1", "value1");
				Intent data = new Intent();
				data.putExtra("paramcallback", "valuecallback");
				PendingIntent pi = createPendingResult(0, data, PendingIntent.FLAG_ONE_SHOT);
				i.putExtra("result", pi);
				mComponentName = startService(i);
			}
		});
		
		btn = (Button)findViewById(R.id.btnStop);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MyService.class);
				stopService(i);
			}
		});
		IntentFilter filter = new IntentFilter(MyService.ACTION_COUNT);
		
		registerReceiver(myReceiver, filter, "com.example.helloservicetest.permission.RECEIVE_COUNT", null);
	}
	
	BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			
			int count = intent.getIntExtra("count", 0);
		}
		
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(myReceiver);
	};
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == RESULT_OK) {
			String resultValue = data.getStringExtra("resultValue");
			String callbackValue = data.getStringExtra("paramcallback");
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
