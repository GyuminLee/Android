package com.example.sample4simplelist;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ListView listView;
	ArrayAdapter<String> mAdapter;
	EditText keywordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		keywordView = (EditText)findViewById(R.id.editText1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					mAdapter.add(keyword);
					keywordView.setText("");
				}
			}
		});
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, new ArrayList<String>());
		listView.setAdapter(mAdapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				String text = (String)listView.getItemAtPosition(position);
				Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
			}
		});
		initData();
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (listView.getChoiceMode() == ListView.CHOICE_MODE_SINGLE) {
					int position = listView.getCheckedItemPosition();
					String text = (String)listView.getItemAtPosition(position);
					Toast.makeText(MainActivity.this, "single choice : " + text, Toast.LENGTH_SHORT).show();
//					mAdapter.remove(text);
				} else if (listView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
					SparseBooleanArray selectlist = listView.getCheckedItemPositions();
					StringBuilder sb = new StringBuilder();
					for (int index = 0; index < selectlist.size(); index++) {
						int position = selectlist.keyAt(index);
						if (selectlist.get(position)) {
							sb.append((String)listView.getItemAtPosition(position));
							sb.append(",");
						}
					}
					Toast.makeText(MainActivity.this, "multi : " + sb.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void initData() {
		String[] array = getResources().getStringArray(R.array.list_item);
		for (String s : array) {
			mAdapter.add(s);
		}
	}
}
