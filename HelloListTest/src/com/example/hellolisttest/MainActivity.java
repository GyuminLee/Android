package com.example.hellolisttest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	EditText itemView;
	ListView listView;
	ArrayAdapter<String> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView) findViewById(R.id.message);
		itemView = (EditText) findViewById(R.id.itemView);
		listView = (ListView) findViewById(R.id.listView1);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		String[] array = getResources().getStringArray(R.array.items);
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.<String> asList(array));
		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, list);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String text = mAdapter.getItem(position);
				messageView.setText("clicked item : " + text);

			}
		});
		Button btn = (Button) findViewById(R.id.btnAdd);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String text = itemView.getText().toString();
				if (text != null && !text.equals("")) {
					mAdapter.add(text);
				}

			}
		});

		btn = (Button) findViewById(R.id.btnChoice);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Single choice
				// String text = mAdapter.getItem(listView
				// .getCheckedItemPosition());
				// Toast.makeText(MainActivity.this, "choice : " + text,
				// Toast.LENGTH_SHORT).show();

				SparseBooleanArray selected = listView
						.getCheckedItemPositions();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < selected.size(); i++) {
					boolean isSelected = selected.get(i);
					if (isSelected) {
						sb.append(mAdapter.getItem(i) + "\n\r");
					}
				}
				Toast.makeText(MainActivity.this, "items : " + sb.toString(),
						Toast.LENGTH_SHORT).show();
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
