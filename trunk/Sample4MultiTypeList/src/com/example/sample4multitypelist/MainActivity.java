package com.example.sample4multitypelist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView listView;
	MyAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new MyAdapter(this);
		listView.setAdapter(mAdapter);
		listView.setStackFromBottom(true);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listView.smoothScrollToPosition(50);
			}
		});
		initData();
	}
	
	private void initData() {
		for (int i = 0; i < 100; i++) {
			MyData d = new MyData();
			if (i % 2 == 0) {
				d.type = MyData.DATA_TYPE_LEFT;
			} else {
				d.type = MyData.DATA_TYPE_RIGHT;
			}
			d.resId = R.drawable.ic_launcher;
			d.message = "message " + i;
			mAdapter.add(d);
		}
		listView.smoothScrollToPosition(99);
	}
}
