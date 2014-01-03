package com.example.sample2servicetest;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.start);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MyService.class);
				Intent data = new Intent();
				data.putExtra("param1", "value1");
				PendingIntent pi = createPendingResult(0, data, PendingIntent.FLAG_ONE_SHOT);
				i.putExtra(MyService.PARAM_SET_COUNT, 100);
				i.putExtra(MyService.PARAM_RESULT_INTENT, pi);
				startService(i);
			}
		});
		btn = (Button)findViewById(R.id.stop);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MyService.class);
				stopService(i);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == RESULT_OK) {
			String callback = data.getStringExtra("param1");
			int count = data.getIntExtra(MyService.PARAM_RESULT_COUNT, 0);
			Toast.makeText(this, "callback : " + callback + ", count : " + count, Toast.LENGTH_SHORT).show();
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
