package com.example.sample4choicelist;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
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
		listView.setAdapter(mAdapter);
		Button btn = (Button)findViewById(R.id.btn_edit);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int mode = listView.getChoiceMode();
				if (mode == ListView.CHOICE_MODE_MULTIPLE) {
					listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
					mAdapter.notifyDataSetChanged();
				} else {
					listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
					listView.clearChoices();
					listView.setItemChecked(1, true);
					listView.setItemChecked(3, true);
					mAdapter.notifyDataSetChanged();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_show);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (listView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
					SparseBooleanArray array = listView.getCheckedItemPositions();
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < array.size(); i++) {
						int position = array.keyAt(i);
						if (array.get(position)) {
							MyData d = (MyData)listView.getItemAtPosition(position);
							sb.append(d.message + ",");
						}
					}
					
					Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		initData();
	}
	
	private void initData() {
		for (int i = 0 ; i < 10 ; i++) {
			MyData d = new MyData();
			d.resId = R.drawable.ic_launcher;
			d.message = "message " + i;
			mAdapter.add(d);
		}
	}
}
