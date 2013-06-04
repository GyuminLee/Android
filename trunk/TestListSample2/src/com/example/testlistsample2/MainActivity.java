package com.example.testlistsample2;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView messageView;
	EditText inputView;
	ListView list;
	ArrayAdapter<String> mAdapter;
	ArrayList<String> mData = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		messageView = (TextView)findViewById(R.id.message);
		inputView = (EditText)findViewById(R.id.inputText);
		list = (ListView)findViewById(R.id.list);
		
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, mData);
		list.setAdapter(mAdapter);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				String str = mAdapter.getItem(position);
				messageView.setText(str);
				Toast.makeText(MainActivity.this, "click item : " + str, Toast.LENGTH_SHORT).show();
			}
		});
		Button btn = (Button)findViewById(R.id.btnAdd);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String addString = inputView.getText().toString();
				mAdapter.add(addString);
			}
		});
		
		btn = (Button)findViewById(R.id.btnChoice);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				int position = list.getCheckedItemPosition();
//				String str = mAdapter.getItem(position);
//				messageView.setText("choice item : " + str);
				SparseBooleanArray selectedArray = list.getCheckedItemPositions();
				StringBuilder sb = new StringBuilder();
				sb.append("selected items : ");
				for (int i = 0; i < mAdapter.getCount(); i++) {
					boolean isSelected = selectedArray.get(i);
					if (isSelected) {
						sb.append(mAdapter.getItem(i) + ",");
					}
				}
				messageView.setText(sb.toString());
			}
		});
		
	}
	
	private void initData() {
		String[] arrays = getResources().getStringArray(R.array.listItem);
		for(int i = 0; i < arrays.length ; i++) {
			mData.add(arrays[i]);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
