package com.example.hellocustomlisttest;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView listView;
	MyAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new MyAdapter(this,initData());
		listView.setAdapter(mAdapter);
	}

	private ArrayList<MyData> initData() {
		ArrayList<MyData> array = new ArrayList<MyData>();
		array.add(new MyData(R.drawable.gallery_photo_1,"abc",39));
		array.add(new MyData(R.drawable.gallery_photo_2,"def",29));
		array.add(new MyData(R.drawable.gallery_photo_3,"ghi",19));		
		return array;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
