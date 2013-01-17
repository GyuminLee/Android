package com.example.testdatabasesample;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class DatabaseShowActivity extends Activity {

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
	    		new int[] {R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4});
	    
	    list.setAdapter(mAdapter);
	    
	    DatabaseModel.getInstance().getDataList(null, new DatabaseModel.OnQueryResultListener() {
			
			@Override
			public void onQueryResult(Cursor c) {
				// TODO Auto-generated method stub
			    startManagingCursor(c);
			    
			    mAdapter.changeCursor(c);
			}
		}, mHandler);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
