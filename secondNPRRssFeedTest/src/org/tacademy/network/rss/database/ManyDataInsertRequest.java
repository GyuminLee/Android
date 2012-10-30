package org.tacademy.network.rss.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class ManyDataInsertRequest extends DataRequest {

	ArrayList<String> mData;
	public ManyDataInsertRequest(ArrayList<String> data) {
		mData = data;
	}
	
	@Override
	public boolean isWritable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void executeSQL(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		
		for (int i = 0; i < mData.size(); i++) {
			String str = mData.get(i);
			
			values.clear();
			values.put("text",str);
			
			db.insert("tablname", null, values);
		}
		
		this.result = true;
	}

}
