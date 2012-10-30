package org.tacademy.network.rss.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class InsertDataRequest extends DataRequest {

	public ContentValues values;
	public String table;
	
	
	@Override
	public boolean isWritable() {
		return true;
	}


	@Override
	protected void executeSQL(SQLiteDatabase db) {
		result = db.insert(table, null, values);
	}

}
