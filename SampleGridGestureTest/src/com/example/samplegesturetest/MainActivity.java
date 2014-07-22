package com.example.samplegesturetest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


public class MainActivity extends Activity {

	GestureListView listView;
	String[] data = {"data0","data1","data2","data3","data4","data5","data6","data7","data8",
			"data0","data1","data2","data3","data4","data5","data6","data7","data8",
			"data0","data1","data2","data3","data4","data5","data6","data7","data8",
			"data0","data1","data2","data3","data4","data5","data6","data7","data8"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (GestureListView)findViewById(R.id.listView1);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data);
		MyAdapter adapter = new MyAdapter(this, data);
		listView.setAdapter(adapter);
		listView.setOnSwipeListener(new OnSwipeListener() {
			
			@Override
			public boolean onSwipe(View v, int orientation) {
				switch(orientation) {
				case GestureGridView.SWIPE_BOTTOM_TO_UP :
				case GestureGridView.SWIPE_UP_TO_BOTTOM :
				case GestureGridView.SWIPE_LEFT_TO_RIGHT :
				case GestureGridView.SWIPE_RIGHT_TO_LEFT :
//					Toast.makeText(MainActivity.this, "fling...", Toast.LENGTH_SHORT).show();
				}
				return true;
			}
		});
		listView.setOnItemSwipeListener(new OnItemSwipeListener() {
			
			@Override
			public boolean onItemSwipe(AdapterView parent, View v, int position,
					int orientation) {
				
				return false;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(MainActivity.this, "onItemClick", Toast.LENGTH_SHORT).show();
			}
		});
		listView.setOnScrollDirectionChangeListener(new OnScrollDirectionChangeListener() {
			
			@Override
			public void onScrollDirectionChanged(View view, int direction) {
				Toast.makeText(MainActivity.this, "direction : " + direction, Toast.LENGTH_SHORT).show();
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
