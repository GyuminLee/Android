package com.example.samplerandomanimation;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	RandomAnimationView aniview;
	EditText intervalView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		intervalView = (EditText)findViewById(R.id.editText1);
		aniview = (RandomAnimationView)findViewById(R.id.animview);
		aniview.setBitmap(((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap());
		Button btn = (Button)findViewById(R.id.btnStart);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				aniview.startAnimation();
			}
		});
		
		btn = (Button)findViewById(R.id.btnPause);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				aniview.pauseAnimation();
			}
		});
		
		btn = (Button)findViewById(R.id.btnInit);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				aniview.initializeAnimation();
			}
		});
		
		btn = (Button)findViewById(R.id.btnInterval);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int interval = Integer.parseInt(intervalView.getText().toString());
				aniview.setAnimationInterval(interval);
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
