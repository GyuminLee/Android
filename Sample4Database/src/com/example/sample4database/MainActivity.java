package com.example.sample4database;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.sample4database.entity.Person;
import com.example.sample4database.manager.DBConstant;
import com.example.sample4database.manager.DataManager;

public class MainActivity extends Activity {

	ListView listView;
	ArrayAdapter<Person> mAdapter;
	SimpleCursorAdapter mCursorAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
//		mAdapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, new ArrayList<Person>());
		String[] from = {DBConstant.PersonTable.FIELD_NAME, DBConstant.PersonTable.FIELD_AGE};
		int[] to = {R.id.column_name, R.id.column_age};
		mCursorAdapter = new SimpleCursorAdapter(this, R.layout.cursor_layout, null, from, to, 0);
		listView.setAdapter(mCursorAdapter);
		
		Button btn = (Button)findViewById(R.id.btn_add);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, DataAddActivity.class);
				startActivity(i);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

	Cursor mCursor;
	private void initData() {
		mCursor = DataManager.getInstance().getPersonCursor();
		mCursorAdapter.swapCursor(mCursor);
//		mAdapter.clear();
//		ArrayList<Person> list = DataManager.getInstance().getPersonList();
//		for (Person p : list) {
//			mAdapter.add(p);
//		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mCursor != null) {
			mCursor.close();
		}
	}
}
