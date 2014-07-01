package com.example.sample4customlist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ListView listView;
	MyAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new MyAdapter(this);
		mAdapter.setOnAdapterItemClickListener(new MyAdapter.OnAdapterItemClickListener() {
			
			@Override
			public void onItemLikeClick(View v, MyData data) {
				Toast.makeText(MainActivity.this, "like click : " + data.name, Toast.LENGTH_SHORT).show();
			}
		});
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MyData d = (MyData)listView.getItemAtPosition(position);
				Toast.makeText(MainActivity.this, "name : " + d.name, Toast.LENGTH_SHORT).show();		
			}
		});
		initData();
	}
	
	private void initData() {
		for (int i = 0 ; i < 10; i++) {
			MyData d = new MyData();
			d.resId = R.drawable.ic_launcher;
			d.name = "name : " + i;
			d.content = "content : " + i;
			mAdapter.add(d);
		}
	}
}
