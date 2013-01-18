package com.example.testdatabasesample;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;

import com.example.testdatabasesample.DatabaseModel.DataFields;

public class DatabaseShowActivity extends FragmentActivity implements LoaderCallbacks<Cursor>{

	ListView list;
	
	SimpleCursorAdapter mAdapter;
	Handler mHandler = new Handler();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    setContentView(R.layout.database_show_layout);
	    
	    list = (ListView)findViewById(R.id.listView1);
	    
	    mAdapter = new SimpleCursorAdapter(this, 
	    		R.layout.item_layout, 
	    		null, 
	    		new String[] {DatabaseModel.DataFields.People.NAME , DatabaseModel.DataFields.People.PHONE,
	    		DatabaseModel.DataFields.People.ADDRESS, DatabaseModel.DataFields.People.AGE}, 
	    		new int[] {R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4}, 0);
	    
	    list.setAdapter(mAdapter);
	    
//	    DatabaseModel.getInstance().getDataList(null, new DatabaseModel.OnQueryResultListener() {
//			
//			@Override
//			public void onQueryResult(Cursor c) {
//				// TODO Auto-generated method stub
//			    startManagingCursor(c);
//			    
//			    mAdapter.changeCursor(c);
//			}
//		}, mHandler);
	    getSupportLoaderManager().initLoader(0, null, this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		String selection = null;
		String[] args = null;
		String name = null;
		String[] projection = { DataFields.People.ID, 
				DataFields.People.NAME, 
				DataFields.People.PHONE, 
				DataFields.People.ADDRESS, 
				DataFields.People.AGE };
		
		if (name != null && !name.equals("")) {
			selection = DataFields.People.NAME + " = ?";
			args = new String[] {name};
		}
		
		MyDBLoader loader = new MyDBLoader(this);
		loader.setParameter(DataFields.People.TABLE_NAME, projection, selection, args, null, null, null);
		
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(arg1);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(null);
	}

}
