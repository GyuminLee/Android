package com.example.samplewindowmanagertest;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				WindowManager.LayoutParams params = new WindowManager.LayoutParams();
				params.height = WindowManager.LayoutParams.WRAP_CONTENT;
				params.width = WindowManager.LayoutParams.WRAP_CONTENT;
				params.format = PixelFormat.TRANSLUCENT;
				params.type = WindowManager.LayoutParams.TYPE_TOAST;
				params.setTitle("Test");
				params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
				Context context = getApplicationContext();
				final WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
				TextView tv = new TextView(MainActivity.this);
				tv.setText("Toast Window Test");
				tv.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						wm.removeView(v);
					}
				});
				params.gravity = Gravity.CENTER;
				params.x = 100;
				params.y = 100;
				wm.addView(tv, params);
			}
		});
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				WindowManager.LayoutParams params = new WindowManager.LayoutParams();
				params.height = WindowManager.LayoutParams.WRAP_CONTENT;
				params.width = WindowManager.LayoutParams.WRAP_CONTENT;
				params.format = PixelFormat.TRANSLUCENT;
				params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
				params.setTitle("Test");
				params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
				Context context = getApplicationContext();
				final WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
				TextView tv = new TextView(MainActivity.this);
				tv.setText("Toast Window Test");
				tv.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						wm.removeView(v);
					}
				});
				params.gravity = Gravity.CENTER;
				params.x = 100;
				params.y = 100;
				wm.addView(tv, params);
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
