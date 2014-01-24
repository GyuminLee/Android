package com.example.samplegesturetest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.samplegesturetest.FlingGridView.OnFlingGestureListener;

public class MainActivity extends Activity {

	FlingGridView listView;
	String[] data = {"data0","data1","data2","data3","data4","data5","data6","data7","data8"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (FlingGridView)findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data);
		listView.setAdapter(adapter);
		listView.setOnFlingGestureListener(new OnFlingGestureListener() {
			
			@Override
			public boolean onFlingGesture(FlingGridView v, int orientation) {
				switch(orientation) {
				case FlingGridView.BOTTOM_TO_UP :
				case FlingGridView.UP_TO_BOTTOM :
				case FlingGridView.LEFT_TO_RIGHT :
				case FlingGridView.RIGHT_TO_LEFT :
					Toast.makeText(MainActivity.this, "fling...", Toast.LENGTH_SHORT).show();
				}
				return false;
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
