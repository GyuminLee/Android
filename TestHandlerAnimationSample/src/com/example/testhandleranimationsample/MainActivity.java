package com.example.testhandleranimationsample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	Handler mHandler = new Handler();
	int mTextColor;
	long startTime;
	int startColor = Color.RED;
	int endColor = Color.BLUE;
	public final static int ANIMATION_DURATION = 2000;
	public final static int ANIMATION_INTERVAL = 100;
	
	public final static int ANIMATION_NOT_STARTED = -1;
	TextView message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		message = (TextView)findViewById(R.id.textView1);
		Button btn = (Button)findViewById(R.id.start);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startTime = ANIMATION_NOT_STARTED;
				mHandler.post(animation);
			}
		});
		
		btn = (Button)findViewById(R.id.stop);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mHandler.removeCallbacks(animation);
			}
		});
	}
	
	Runnable animation = new Runnable() {
		
		@Override
		public void run() {
			if (startTime == ANIMATION_NOT_STARTED) {
				startTime = System.currentTimeMillis();
			}
			
			long currentTime = System.currentTimeMillis();
			int delay = (int)(currentTime - startTime);
			boolean reverse = ((delay / ANIMATION_DURATION) % 2) == 1;
			int time = (delay % ANIMATION_DURATION);
			if (reverse == false) {
				int red = 0xFF * (ANIMATION_DURATION - time) / ANIMATION_DURATION;
				int green = 0;
				int blue = 0xFF * time / ANIMATION_DURATION;
				int color = Color.rgb(red, green, blue);
				mTextColor = color;
			} else {
				int red = 0xFF * time / ANIMATION_DURATION;
				int green = 0;
				int blue = 0xFF * (ANIMATION_DURATION - time) / ANIMATION_DURATION;
				int color = Color.rgb(red, green, blue);
				mTextColor = color;
			}
			message.setTextColor(mTextColor);
			mHandler.postDelayed(this, ANIMATION_INTERVAL);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
