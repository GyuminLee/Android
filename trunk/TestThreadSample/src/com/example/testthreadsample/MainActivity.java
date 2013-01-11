package com.example.testthreadsample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements Runnable {

	TextView tv;
	
	
	static final int MESSAGE_ID_UPDATE_TEXTVIEW = 1;
	
	
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch(msg.what) {
			case MESSAGE_ID_UPDATE_TEXTVIEW :
				String text = (String)msg.obj;
				tv.setText(text);
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView)findViewById(R.id.textView1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(MainActivity.this).start();
			}
		});
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int count = 0;
		while(count < 20) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count++;
			Message msg = mHandler.obtainMessage(MESSAGE_ID_UPDATE_TEXTVIEW, "count : " + count);
			mHandler.sendMessage(msg);
//			tv.setText("count : " + count);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
