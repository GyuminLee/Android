package com.example.sampleexpandablelist;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;

public class MainActivity extends Activity {

	ExpandableListView listView;
	MyAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ExpandableListView)findViewById(R.id.expandableListView1);
		mAdapter = new MyAdapter(this);
		listView.setAdapter(mAdapter);
		initData();
		for (int i = 0; i < mAdapter.getGroupCount(); i++) {
			listView.expandGroup(i);
		}
		listView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			
			@Override
			public void onGroupCollapse(int groupPosition) {
				listView.expandGroup(groupPosition);
			}
		});
	}
	
	private void initData() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				mAdapter.put("key"+i, " ======= data"+j);
			}
		}
	}
}
