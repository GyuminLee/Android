package com.example.sample2database;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText nameView, ageView;
	ListView listView;
	SimpleCursorAdapter mAdapter;
	String[] columns = { DBConstants.PersonTable._ID,
			DBConstants.PersonTable.COLUMN_NAME,
			DBConstants.PersonTable.COLUMN_AGE };
	Cursor mCursor = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		nameView = (EditText) findViewById(R.id.nameView);
		ageView = (EditText) findViewById(R.id.ageView);
		listView = (ListView) findViewById(R.id.listView1);
		mCursor = DBModel.getInstance().query(columns);
		mAdapter = new SimpleCursorAdapter(this, R.layout.item_layout, mCursor,
				new String[] { DBConstants.PersonTable.COLUMN_NAME,
						DBConstants.PersonTable.COLUMN_AGE }, new int[] {
						R.id.name, R.id.age }, 0);
		listView.setAdapter(mAdapter);
		
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
