package org.tacademy.network.rss.npr;

import org.tacademy.network.rss.ParentActivity;
import org.tacademy.network.rss.R;
import org.tacademy.network.rss.database.DBScheme;
import org.tacademy.network.rss.database.DataProcessThread;
import org.tacademy.network.rss.database.DataRequest;
import org.tacademy.network.rss.database.DatabaseHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

public class NPRDBDetails extends ParentActivity {

	NewsDBAdapter adapter;
	
	ListView listView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.detail);
	    listView = (ListView)findViewById(R.id.ListView01);
	    // TODO Auto-generated method stub
	}
	
	DataRequest.DataProcessedListener selectListener = new DataRequest.DataProcessedListener() {
		
		public void OnDataProcessed(int result, DataRequest request) {
			RssNews news = (RssNews)request.getResult();
			RssItemAdapter adapter = new RssItemAdapter(getApplicationContext(),news.items);
			listView.setAdapter(adapter);
		}
	};
	@Override
	protected void onResume() {
		super.onResume();
		
		RssSelectRequest request = new RssSelectRequest();
		request.setDataProcessedListener(selectListener);
		DataProcessThread th = DataProcessThread.getInstance();
		th.enqueue(request);
//		
//		adapter = new NewsDBAdapter(this,getDBNews());
//		
//		listView.setAdapter(adapter);
//		
//		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//			public void onItemClick(AdapterView<?> listview, View view, int position, long itemid) {
//				// ... 
//			}
//		});
		
		
	}
	
	public Cursor getDBNews() {
		DatabaseHelper helper = new DatabaseHelper(this);
		SQLiteDatabase db = helper.getReadableDatabase();
		String[] columns = { DBScheme.ItemTable.ID , DBScheme.ItemTable.TITLE, 
			DBScheme.ItemTable.DESCRIPTION, DBScheme.ItemTable.LINK,
			DBScheme.ItemTable.PUBLISH_DATE, DBScheme.ItemTable.CONTENT
		};
		
		Cursor c = db.query(DBScheme.ItemTable.TABLE_NAME, columns, null, null, null, null, null);
		
		return c;
	}
	
	

}
