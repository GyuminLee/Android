package org.tacademy.network.rss.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

public class LogInsertRequest extends DataRequest {

	ArrayList<String> mData;
	public LogInsertRequest(ArrayList<String> data) {
		mData = data;
	}
	
	@Override
	public boolean process(SQLiteDatabase db, Handler handler) {
		// TODO Auto-generated method stub
		executeSQL(db);
		if (listener != null) {
			listener.OnDataProcessed(PROCESS_SUCCESS, this);
		}
		return true;
	}

	@Override
	protected void executeSQL(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ContentValues values = new ContentValues();
		int id;
		for (String data : mData) {
			values.clear();
			values.put("data", data);
			id = (int)db.insert("table", null, values);
			ids.add((Integer)id);
		}
		this.result = ids;
	}

	@Override
	public boolean isWritable() {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
