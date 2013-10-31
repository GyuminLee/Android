package com.example.helloviewanimationtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageView imageView;
	ImageView imageView2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		Button btn = (Button) findViewById(R.id.btnRotate);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				Animation anim = AnimationUtils.loadAnimation(
//						MainActivity.this, R.anim.set_view);
				Animation anim = new MyAnimation();
				anim.setDuration(2000);
				anim.setFillAfter(true);
				anim.setInterpolator(new LinearInterpolator());
				anim.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.set_view);
						imageView2.startAnimation(anim);
					}
				});
				imageView.startAnimation(anim);

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
