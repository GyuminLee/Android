package com.example.sample2listselectitem;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ListView listView;
	MyAdapter mAdapter;
	String[] data = {"data1","data2","data3","data4"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new MyAdapter(this,data);
		listView.setAdapter(mAdapter);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
				mAdapter.setCheckable(true);
			}
		});
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (listView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
					SparseBooleanArray array = listView.getCheckedItemPositions();
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < array.size(); i++) {
						if (array.get(i)) {
							sb.append(listView.getItemAtPosition(i).toString()+",");
						}
					}
					listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
					mAdapter.setCheckable(false);
					Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
				}
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
