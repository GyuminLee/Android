package com.example.sampledatabasetest;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView list;
	MyDatabaseOpenHelper mDBHelper;
	Cursor mCursor = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.btnAdd);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, AddDBActivity.class);
				startActivity(i);
			}
		});
		mDBHelper = new MyDatabaseOpenHelper(this, null, null, 0);
		list = (ListView)findViewById(R.id.listView1);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (mCursor != null) {
			mCursor.close();
			mCursor = null;
		}
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		String sql = "SELECT "+DBConstant.PersonTable.ID+", "+DBConstant.PersonTable.NAME+" , "+DBConstant.PersonTable.AGE
				+" FROM " + DBConstant.PersonTable.TABLE_NAME;
		mCursor = db.rawQuery(sql, null);
		String[] from = { DBConstant.PersonTable.NAME, DBConstant.PersonTable.AGE };
		int[] to = { R.id.fieldName, R.id.fieldAge };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item_layout, mCursor, from, to, 0);
		list.setAdapter(adapter);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (mCursor != null) {
			mCursor.close();
		}
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
