package com.example.sample4gridview;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {

	GridView gridView;
	ArrayAdapter<String> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gridView = (GridView)findViewById(R.id.gridView1);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
		gridView.setAdapter(mAdapter);
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String text = (String)gridView.getItemAtPosition(position);
				Toast.makeText(MainActivity.this, "item : " + position, Toast.LENGTH_SHORT).show();
			}
		});
		
		initData();
	}
	
	private void initData() {
		for (int i = 0; i < 10; i++) {
			mAdapter.add("item " + i);
		}
	}
}
