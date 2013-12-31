package com.example.sampleframelayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	int mIndex = 0;
	ImageView imageView1;
	ImageView imageView2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView1 = (ImageView)findViewById(R.id.imageView1);
		imageView2 = (ImageView)findViewById(R.id.imageView2);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mIndex == 0) {
					imageView1.setVisibility(View.VISIBLE);
					mIndex = 1;
				} else if (mIndex == 1) {
					imageView2.setVisibility(View.VISIBLE);
					mIndex = 0;
				}
			}
		});
		imageView1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imageView1.setVisibility(View.GONE);
			}
		});
		imageView2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imageView2.setVisibility(View.GONE);
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
