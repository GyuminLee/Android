package com.example.sample3list;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	TextView messageView;
	EditText keywordView;

	ArrayAdapter<String> mAdapter;
	ArrayList<String> mData = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		messageView = (TextView) findViewById(R.id.messageView);
		keywordView = (EditText) findViewById(R.id.editKeyowrd);

		makeData();
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, mData);
		listView.setAdapter(mAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String text = (String) listView.getItemAtPosition(position);
				messageView.setText(text);
			}
		});

		Button btn = (Button) findViewById(R.id.btnAdd);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mAdapter.add(keywordView.getText().toString());
			}
		});
		// if (savedInstanceState == null) {
		// getSupportFragmentManager().beginTransaction()
		// .add(R.id.container, new PlaceholderFragment()).commit();
		// }

		btn = (Button) findViewById(R.id.btnChoice);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listView.getChoiceMode() == ListView.CHOICE_MODE_SINGLE) {
					int position = listView.getCheckedItemPosition();
					String text = (String) listView.getItemAtPosition(position);
					Toast.makeText(MainActivity.this, "choice item : " + text,
							Toast.LENGTH_SHORT).show();
				} else if (listView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
					SparseBooleanArray selectedList = listView
							.getCheckedItemPositions();
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < selectedList.size(); i++) {
						int position = selectedList.keyAt(i);
						if (selectedList.get(position)) {
							String text = (String) listView
									.getItemAtPosition(position);
							sb.append(text);
							sb.append(",");
						}
					}
					Toast.makeText(MainActivity.this,
							"selected Items : " + sb.toString(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void makeData() {
		String[] array = getResources().getStringArray(R.array.listItem);
		Collections.addAll(mData, array);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	// public static class PlaceholderFragment extends Fragment {
	//
	// public PlaceholderFragment() {
	// }
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View rootView = inflater.inflate(R.layout.fragment_main, container,
	// false);
	// return rootView;
	// }
	// }

}
