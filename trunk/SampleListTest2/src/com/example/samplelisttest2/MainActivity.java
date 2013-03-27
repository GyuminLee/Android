package com.example.samplelisttest2;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	ListView listView;
	TextView messageView;
	EditText addTextView;
//	ArrayAdapter<MyData> mAdapter;
	MyAdapter mAdapter;
	MyData[] mData = {new MyData("aaa","descAAA") , 
					new MyData("bbb","descBBB") , 
					new MyData("ccc","descCCC") };
	ArrayList<MyData> mList = new ArrayList<MyData>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.myListView);
		messageView = (TextView)findViewById(R.id.message);
		addTextView = (EditText)findViewById(R.id.addText);
		setData();
		
//		mAdapter = new ArrayAdapter<MyData>(this, 
//				android.R.layout.simple_list_item_1, 
//				mList);
		mAdapter = new MyAdapter(this, mList);
		
		listView.setAdapter(mAdapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				messageView.setText("clicked item : " + mAdapter.getItem(position));
			}
		});
		
		Button btn = (Button)findViewById(R.id.add);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String text = addTextView.getText().toString();
				if (text != null && !text.equals("")) {
					mAdapter.add(new MyData(text,"desc"+text));
				}
			}
		});
		
	}
	
	private void setData() {
		for (MyData str : mData) {
			mList.add(str);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
