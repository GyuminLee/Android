package com.example.testservicesample;

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
		
		Button btn = (Button)findViewById(R.id.startService);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, MyService.class);
				Intent resultIntent = new Intent();
				resultIntent.putExtra("test", "aaa");
				PendingIntent pi = createPendingResult(0, resultIntent , PendingIntent.FLAG_ONE_SHOT);
				i.putExtra("resultPI",pi);
				i.putExtra("count", 10);
				startService(i);
			}
		});
		
		btn = (Button)findViewById(R.id.stopService);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, MyService.class);
				stopService(i);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 0) {
			if (resultCode == Activity.RESULT_OK) {
				int count = data.getIntExtra("count", -1);
				String test = data.getStringExtra("test");
				Toast.makeText(this, "count : " + count + ", test : " + test, 
						Toast.LENGTH_SHORT).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
