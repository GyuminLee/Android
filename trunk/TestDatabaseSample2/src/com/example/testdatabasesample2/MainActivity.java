package com.example.testdatabasesample2;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
