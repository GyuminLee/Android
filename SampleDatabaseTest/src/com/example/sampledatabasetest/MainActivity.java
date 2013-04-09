package com.example.sampledatabasetest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.sampledatabasetest.manager.DBConstant;
import com.example.sampledatabasetest.manager.DBManager;
import com.example.sampledatabasetest.manager.DBRequest;
import com.example.sampledatabasetest.manager.MyDatabaseOpenHelper;
import com.example.sampledatabasetest.manager.PersonCursorLoader;
import com.example.sampledatabasetest.manager.PersonRequest;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

	ListView list;
	MyDatabaseOpenHelper mDBHelper;
	Cursor mCursor = null;

	SimpleCursorAdapter mAdapter;
	Handler mHandler = new Handler();
	
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
		getSupportLoaderManager().initLoader(0, null, this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
//		if (mCursor != null) {
//			mCursor.close();
//			mCursor = null;
//		}
//		SQLiteDatabase db = mDBHelper.getReadableDatabase();
//		String sql = "SELECT "+DBConstant.PersonTable.ID+", "+DBConstant.PersonTable.NAME+" , "+DBConstant.PersonTable.AGE
//				+" FROM " + DBConstant.PersonTable.TABLE_NAME + " WHERE " +
//				DBConstant.PersonTable.AGE + " > ? AND " +
//				DBConstant.PersonTable.AGE + " < ?";
//		String[] argument = { "20" , "60" };
//		mCursor = db.rawQuery(sql, argument);

//		PersonRequest request = new PersonRequest(20, 60);
//		DBManager.getInstance().getAsyncDBProcess(request, new DBRequest.OnQueryCompleteListener() {
//			
//			@Override
//			public void onQueryCompleted(DBRequest request) {
//				// TODO Auto-generated method stub
//				Cursor c = (Cursor)request.getResult();
//				if (mCursor != null) {
//					mCursor.close();
//					mCursor = null;
//				}
//				
//				mCursor = c;
//				mAdapter.swapCursor(mCursor);
//			}
//		}, mHandler);
		
//		mCursor = DBManager.getInstance().getPersonList(20, 60);
//		
//		mAdapter.swapCursor(mCursor);
		

		getSupportLoaderManager().restartLoader(0, null, this);
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

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return new PersonCursorLoader(this, 20, 60);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor c) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(c);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(null);
	}

}
