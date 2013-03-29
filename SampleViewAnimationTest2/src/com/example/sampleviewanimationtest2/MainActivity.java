package com.example.sampleviewanimationtest2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageView targetView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		targetView = (ImageView)findViewById(R.id.aniTarget);
		Button btn = (Button)findViewById(R.id.rotate);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Animation ani = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_ani);
				targetView.startAnimation(ani);
			}
		});
		
		btn = (Button)findViewById(R.id.scale);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Animation ani = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_ani);
				targetView.startAnimation(ani);
			}
		});
		
		btn = (Button)findViewById(R.id.set);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Animation ani = AnimationUtils.loadAnimation(MainActivity.this, R.anim.set_ani);
				targetView.startAnimation(ani);
			}
		});
		
		btn = (Button)findViewById(R.id.scaleAfterRotate);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Animation ani = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_ani);
				ani.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						Animation ani = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_ani);
						targetView.startAnimation(ani);
					}
				});
				targetView.startAnimation(ani);
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
