package com.example.sample2database;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText nameView, ageView;
	ListView listView;
	SimpleCursorAdapter mAdapter;
	String[] columns = { DBConstants.PersonTable._ID,
			DBConstants.PersonTable.COLUMN_NAME,
			DBConstants.PersonTable.COLUMN_AGE };
	Cursor mCursor = null;
	TextView messageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		nameView = (EditText) findViewById(R.id.nameView);
		ageView = (EditText) findViewById(R.id.ageView);
		listView = (ListView) findViewById(R.id.listView1);
		messageView = (TextView)findViewById(R.id.messageView);
		
		mCursor = DBModel.getInstance().query(columns);
		mAdapter = new SimpleCursorAdapter(this, R.layout.item_layout, mCursor,
				new String[] { DBConstants.PersonTable.COLUMN_NAME,
						DBConstants.PersonTable.COLUMN_AGE }, new int[] {
						R.id.name, R.id.age }, 0);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				messageView.setText("" + id);
				Cursor c = (Cursor)listView.getItemAtPosition(position);
				int nameIndex = c.getColumnIndex(DBConstants.PersonTable.COLUMN_NAME);
				int ageIndex = c.getColumnIndex(DBConstants.PersonTable.COLUMN_AGE);
				String name = c.getString(nameIndex);
				int age = c.getInt(ageIndex);
				nameView.setText(name);
				ageView.setText("" + age);
			}
		});
		
		Button btn = (Button) findViewById(R.id.btnAdd);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Person p = new Person();
				p.name = nameView.getText().toString();
				p.age = Integer.parseInt(ageView.getText().toString());
				DBModel.getInstance().insert(p);
				Toast.makeText(MainActivity.this, "added", Toast.LENGTH_SHORT)
						.show();
				mCursor = DBModel.getInstance().query(columns);
				mAdapter.swapCursor(mCursor);
			}
		});
		btn = (Button)findViewById(R.id.btnModify);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Person p = new Person();
				try {
					int id = Integer.parseInt(messageView.getText().toString());
					p.id = id;
					p.name = nameView.getText().toString();
					p.age = Integer.parseInt(ageView.getText().toString());
					DBModel.getInstance().update(p);
					mCursor = DBModel.getInstance().query(columns);
					mAdapter.swapCursor(mCursor);
				} catch (NumberFormatException e) {
					e.printStackTrace();
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
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mCursor != null) {
			mCursor.close();
			mCursor = null;
		}
	}

}
