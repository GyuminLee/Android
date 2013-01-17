package com.example.testdatabasesample;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class DatabaseShowActivity extends Activity {

	ListView list;
	
	SimpleCursorAdapter mAdapter;	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    setContentView(R.layout.database_show_layout);
	    
	    list = (ListView)findViewById(R.id.listView1);
	    
	    mAdapter = new SimpleCursorAdapter(this, 
	    		android.R.layout.simple_list_item_1, 
	    		null, 
	    		new String[] {DatabaseModel.DataFields.People.NAME}, 
	    		new int[] {android.R.id.text1});
	    
	    list.setAdapter(mAdapter);
	    
	    Cursor c = DatabaseModel.getInstance().getDataList(null);
	    
	    startManagingCursor(c);
	    
	    mAdapter.changeCursor(c);
	    
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
