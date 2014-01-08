package com.example.sample2viewanimation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity {

	ImageView imageView;
	ListView listView;
	String[] items = {"item1","item2","item3","item4","item5","item6","item7"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.imageView1);
		listView = (ListView)findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		listView.setAdapter(adapter);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Animation ani = AnimationUtils.loadAnimation(MainActivity.this,
						R.anim.left_appear);
				imageView.setVisibility(View.VISIBLE);
				imageView.startAnimation(ani);
			}
		});
		btn = (Button) findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Animation ani = AnimationUtils.loadAnimation(MainActivity.this,
						R.anim.left_disappear);
				ani.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						imageView.setVisibility(View.GONE);
					}
				});
				imageView.startAnimation(ani);

			}
		});
		btn = (Button)findViewById(R.id.button3);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Animation3D ani = new Animation3D();
				ani.setDuration(1000);
				ani.setFillAfter(true);
				listView.startAnimation(ani);
			}
		});
		btn = (Button)findViewById(R.id.button4);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MyActivity.class);
				overridePendingTransition(R.anim.left_appear, R.anim.left_disappear);
				startActivity(i);
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
