package com.example.testviewanimationsample2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageView target;
	ImageView iv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		target = (ImageView)findViewById(R.id.target);
		iv = (ImageView)findViewById(R.id.imageView1);
		Button btn = (Button)findViewById(R.id.translate);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate_anim);
				target.startAnimation(anim);
			}
		});
		
		btn = (Button)findViewById(R.id.scale);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scal_anim);
				AccelerateDecelerateInterpolator inter = new AccelerateDecelerateInterpolator();
				anim.setInterpolator(inter);
				MyInterpolator i = new MyInterpolator();
				anim.setInterpolator(i);
				target.startAnimation(anim);
			}
		});
		btn = (Button)findViewById(R.id.rotate);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_anim);
				Animation anim2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_anim);
				anim2.setStartOffset(1000);
				target.startAnimation(anim);
				iv.startAnimation(anim2);
			}
		});
		
		btn = (Button)findViewById(R.id.alpha);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha_anim);
				target.startAnimation(anim);
			}
		});
		
		btn = (Button)findViewById(R.id.setAnim);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.set_anim);
				target.startAnimation(anim);
			}
		});
		btn = (Button)findViewById(R.id.serialize);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scal_anim);
				anim.setAnimationListener(new AnimationListener() {
					
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
						Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_anim);
						target.startAnimation(anim);
					}
				});
				target.startAnimation(anim);
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
