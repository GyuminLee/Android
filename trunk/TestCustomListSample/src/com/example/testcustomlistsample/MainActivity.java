package com.example.testcustomlistsample;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView messageView;
	EditText inputView;
	ListView list;
	MyAdapter mAdapter;
	ArrayList<MyData> mData = new ArrayList<MyData>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		messageView = (TextView)findViewById(R.id.message);
		inputView = (EditText)findViewById(R.id.inputText);
		list = (ListView)findViewById(R.id.list);
		mAdapter = new MyAdapter(this, mData);
		list.setAdapter(mAdapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				MyData str = (MyData)mAdapter.getItem(position);
				messageView.setText(str.name);
			}
		});
		
		Button btn = (Button)findViewById(R.id.add);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String str = inputView.getText().toString();
				mAdapter.add(new MyData(str,39,"desc:" + str));
			}
		});
	}
	
	private void initData() {
		String[] arrays = getResources().getStringArray(R.array.listItem);
		for (int i = 0; i < arrays.length; i++) {
			String[] item = arrays[i].split("|");
			mData.add(new MyData(item[0], Integer.parseInt(item[1]), item[2]));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
