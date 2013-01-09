package com.example.testlistsample;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ListView listView;
	EditText editTitle;
	
	String[] mList = {"item1" , "item2" , "item3" };
	ArrayList<MyData> mMyDataList = new ArrayList<MyData>();
	MyAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		
		listView = (ListView)findViewById(R.id.listView1);
		adapter = new MyAdapter(this,mMyDataList);
		adapter.setOnItemImageClickListener(new MyAdapter.OnItemImageClickListener() {
			
			@Override
			public void onItemImageClick(MyData data) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, 
						"item image click : " + data.title, 
						Toast.LENGTH_SHORT).show();
			}
		});
		listView.setAdapter(adapter);
		
		editTitle = (EditText)findViewById(R.id.editTitle);
		
		Button btn = (Button)findViewById(R.id.btnAdd);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String title = editTitle.getText().toString();
				String desc = "desc" + title;
				MyData data = new MyData(R.drawable.ic_launcher, title, desc);
				adapter.add(data);
			}
		});
		
//		ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_list_item_1, mList);
//		listView.setAdapter(mAdapter);
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position,
//					long id) {
//				// TODO Auto-generated method stub
//				Toast.makeText(MainActivity.this, "click text : " + mList[position], 
//						Toast.LENGTH_SHORT).show();
//			}
//		});
	}
	
	void initData() {
		mMyDataList.add(new MyData(R.drawable.ic_launcher,"title1","desc1"));
		mMyDataList.add(new MyData(R.drawable.ic_launcher,"title2","desc2"));
		mMyDataList.add(new MyData(R.drawable.ic_launcher,"title3","desc3"));
		mMyDataList.add(new MyData(R.drawable.ic_launcher,"title4","desc4"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
