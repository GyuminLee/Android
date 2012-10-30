package org.tacademy.network.rss.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RawQueryRequest extends DataRequest {

	String query;
	String[] args;
	
	@Override
	protected void executeSQL(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Cursor c = db.rawQuery(query, args);
		result = c;
	}

}
