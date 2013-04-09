package com.example.sampledatabasetest;

import com.example.sampledatabasetest.manager.DBConstant;
import com.example.sampledatabasetest.manager.DBManager;
import com.example.sampledatabasetest.manager.MyDatabaseOpenHelper;

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

	SimpleCursorAdapter mAdapter;
	
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
		String[] from = { DBConstant.PersonTable.NAME, DBConstant.PersonTable.AGE };
		int[] to = { R.id.fieldName, R.id.fieldAge };
		mAdapter = new SimpleCursorAdapter(this, R.layout.item_layout, null, from, to, 0);
		list.setAdapter(mAdapter);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (mCursor != null) {
//			stopManagingCursor(mCursor);
			mCursor.close();
			mCursor = null;
		}
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
//		String sql = "SELECT "+DBConstant.PersonTable.ID+", "+DBConstant.PersonTable.NAME+" , "+DBConstant.PersonTable.AGE
//				+" FROM " + DBConstant.PersonTable.TABLE_NAME + " WHERE " +
//				DBConstant.PersonTable.AGE + " > ? AND " +
//				DBConstant.PersonTable.AGE + " < ?";
//		String[] argument = { "20" , "60" };
//		mCursor = db.rawQuery(sql, argument);
		
		mCursor = DBManager.getInstance().getPersonList(20, 60);
		
		mAdapter.swapCursor(mCursor);
		
//		startManagingCursor(mCursor);
		
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
