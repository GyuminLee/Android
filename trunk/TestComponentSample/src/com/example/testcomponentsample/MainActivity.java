package com.example.testcomponentsample;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private final static int REQUEST_CODE_MY_ACTIVITY = 0;
	private final static int REQUEST_CODE_GET_CONTENT = 1;
	private final static int REQUEST_CODE_SERVICE = 2;
	
	ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView)findViewById(R.id.imageView1);
		
		Button btn = (Button)findViewById(R.id.showDial);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW,
						Uri.parse("content://contacts/people/1"));
				startActivity(i);				
			}
		});
		
		btn = (Button)findViewById(R.id.getPicture);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/*");
				startActivityForResult(i,REQUEST_CODE_GET_CONTENT);
			}
		});
		
		btn = (Button)findViewById(R.id.showMyActivity);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, MyActivity.class);
				Person p = new Person("ysi", 39);
				i.putExtra(MyActivity.PARAM_PERSON, p);
				
				Person[] ps = new Person[2];
				ps[0] = new Person("1",1);
				ps[1] = new Person("2",2);
				i.putExtra("ps", ps);
//				i.putExtra(MyActivity.PARAM_NAME, "ysi");
//				i.putExtra(MyActivity.PARAM_AGE, 39);
//				startActivity(i);
				startActivityForResult(i, REQUEST_CODE_MY_ACTIVITY);
			}
		});
		
		
		btn = (Button)findViewById(R.id.startService);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, MyService.class);
				Intent data = new Intent();
				data.putExtra("activityParam1", "avalue");
				PendingIntent pi = createPendingResult(REQUEST_CODE_SERVICE, data, PendingIntent.FLAG_ONE_SHOT);
				
				i.putExtra("param1", "value1");
				i.putExtra("resultPI", pi);
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
		
		IntentFilter filter = new IntentFilter(MyService.ACTION_TEN_COUNT);
		registerReceiver(mReceiver, filter);
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}
	
	BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(context, "receive service count : " + intent.getIntExtra("count", 0), Toast.LENGTH_SHORT).show();
		}
		
	};
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_MY_ACTIVITY) {
			if (resultCode == Activity.RESULT_OK) {
				String status = data.getStringExtra(MyActivity.RESULT_PARAM_STATUS);
				Toast.makeText(this, "status : " + status, Toast.LENGTH_SHORT).show();
				return;
			}
		} else if (requestCode == REQUEST_CODE_GET_CONTENT) {
			if (resultCode == Activity.RESULT_OK) {
				Uri uri = data.getData();
				imageView.setImageURI(uri);
				return;
			}
		} else if (requestCode == REQUEST_CODE_SERVICE) {
			if (resultCode == Activity.RESULT_OK) {
				String activityParam1 = data.getStringExtra("activityParam1");
				String serviceParam1 = data.getStringExtra("serviceParam1");
				Toast.makeText(this, "onActivityResult : " + serviceParam1, Toast.LENGTH_SHORT).show();
			}
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
