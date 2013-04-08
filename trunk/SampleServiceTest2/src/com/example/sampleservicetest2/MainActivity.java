package com.example.sampleservicetest2;

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
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent dataIntent = new Intent();
				dataIntent.putExtra("param1", "value1");
				PendingIntent resultPI = createPendingResult(0, dataIntent, PendingIntent.FLAG_ONE_SHOT);
				
				
				
				Intent serviceIntent = new Intent(MainActivity.this, MyService.class);
				serviceIntent.putExtra("service_param1", "value1");
				serviceIntent.putExtra("result", resultPI);
				startService(serviceIntent);
			}
		});
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent serviceIntent = new Intent(MainActivity.this, MyService.class);
				stopService(serviceIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 0) {
			if (resultCode == Activity.RESULT_OK) {
				String activityValue = data.getStringExtra("param1");
				String serviceValue = data.getStringExtra("service_result");
				Toast.makeText(this, "p1 : " + activityValue + ",p2 : " + serviceValue, Toast.LENGTH_SHORT).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
