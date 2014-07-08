package com.example.sample4viewanimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageView imageView;
	ImageView imageView2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView)findViewById(R.id.imageView1);
		imageView2 = (ImageView)findViewById(R.id.imageView2);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.view_translate);
//				anim.setAnimationListener(new AnimationListener() {
//					
//					@Override
//					public void onAnimationStart(Animation animation) {
//						
//					}
//					
//					@Override
//					public void onAnimationRepeat(Animation animation) {
//						
//					}
//					
//					@Override
//					public void onAnimationEnd(Animation animation) {
//						imageView.setVisibility(View.GONE);
//					}
//				});
				imageView.startAnimation(anim);
				Animation anim2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.view_translate2);
				imageView2.startAnimation(anim2);
				
			}
		});
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.view_rotate);
				imageView.startAnimation(anim);
			}
		});
		btn = (Button)findViewById(R.id.button3);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.view_scale);
				imageView.startAnimation(anim);
			}
		});
		btn = (Button)findViewById(R.id.button4);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.view_alpha);
				imageView.startAnimation(anim);
			}
		});
		
		btn = (Button)findViewById(R.id.button5);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.view_set);
				imageView.startAnimation(anim);
			}
		});
		btn = (Button)findViewById(R.id.button6);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.view_set2);
				imageView.startAnimation(anim);
			}
		});
		
		btn = (Button)findViewById(R.id.button7);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyAnimation anim = new MyAnimation();
				anim.setDuration(2000);
				imageView.startAnimation(anim);
			}
		});
		
		btn = (Button)findViewById(R.id.button8);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				My3DAnimation anim = new My3DAnimation();
				anim.setDuration(2000);
				imageView.startAnimation(anim);
			}
		});
	}
}
