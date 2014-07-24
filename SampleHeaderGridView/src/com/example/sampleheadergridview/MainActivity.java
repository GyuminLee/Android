package com.example.sampleheadergridview;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

	HeaderGridView hgv;
	ArrayAdapter<String> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		hgv = (HeaderGridView)findViewById(R.id.header_grid_view);
		mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
		TextView tv = (TextView)getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
		tv.setText("Header...");
		hgv.addHeaderView(tv);
		hgv.setAdapter(mAdapter);
		initData();
	}
	
	private void initData() {
		for (int i = 0; i < 10; i++) {
			mAdapter.add("item"+i);
		}
	}
}
