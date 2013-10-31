package com.example.helloframeanimationtest;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageView aniImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		aniImage = (ImageView)findViewById(R.id.imageView1);
		Button btn = (Button)findViewById(R.id.start);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AnimationDrawable drawable = 
						(AnimationDrawable)aniImage.getDrawable();
				drawable.start();
			}
		});
		btn = (Button)findViewById(R.id.stop);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AnimationDrawable drawable =
						(AnimationDrawable)aniImage.getDrawable();
				drawable.stop();
			}
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			AnimationDrawable drawable = (AnimationDrawable)aniImage.getDrawable();
			drawable.start();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
