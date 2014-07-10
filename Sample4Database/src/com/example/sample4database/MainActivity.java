package com.example.sample4database;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.sample4database.entity.Person;
import com.example.sample4database.manager.DataManager;

public class MainActivity extends Activity {

	ListView listView;
	ArrayAdapter<Person> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, new ArrayList<Person>());
		listView.setAdapter(mAdapter);
		
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
	private void initData() {
		mAdapter.clear();
		ArrayList<Person> list = DataManager.getInstance().getPersonList();
		for (Person p : list) {
			mAdapter.add(p);
		}
	}
}
