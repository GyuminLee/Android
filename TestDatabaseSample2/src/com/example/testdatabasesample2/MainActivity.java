package com.example.testdatabasesample2;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testdatabasesample2.DBConstant.PersonTable;

public class MainActivity extends Activity {

	EditText editNameView;
	EditText editAddressView;
	ListView list;
	Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editNameView = (EditText)findViewById(R.id.editName);
		editAddressView = (EditText)findViewById(R.id.editAddress);
		list = (ListView)findViewById(R.id.listView1);
		Button btn = (Button)findViewById(R.id.btnAdd);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = editNameView.getText().toString();
				String address = editNameView.getText().toString();
				Person p = new Person();
				p.name = name;
				p.address = address;
				DatabaseManager.getInstance().savePerson(p);
			}
		});
		
		btn = (Button)findViewById(R.id.btnShow);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				DatabaseManager.getInstance().getPersonTable(new DatabaseManager.OnQueryCompleteListener() {
					
					@Override
					public void onCompleted(ArrayList<Person> personlist) {
						ArrayAdapter<Person> adapter = new ArrayAdapter<Person>(MainActivity.this,
								android.R.layout.simple_list_item_1, personlist);
						list.setAdapter(adapter);
					}
				}, mHandler);
			}
		});
		
		btn = (Button)findViewById(R.id.getCursor);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatabaseManager.getInstance().getPersonCurosr(new DatabaseManager.OnQueryCompleteListener2() {
					
					@Override
					public void onCompleted(Cursor cursor) {
						String[] from = {PersonTable.NAME, PersonTable.ADDRESS};
						int[] to = {R.id.name, R.id.address};
						SimpleCursorAdapter adapter = new SimpleCursorAdapter(
								MainActivity.this, 
								R.layout.item_layout, 
								cursor, 
								from, 
								to, 
								0);
						list.setAdapter(adapter);
						list.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								Toast.makeText(MainActivity.this, "id : " + id, Toast.LENGTH_SHORT).show();
							}
						});
					}
				}, mHandler);
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
