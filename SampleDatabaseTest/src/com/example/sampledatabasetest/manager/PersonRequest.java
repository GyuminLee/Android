package com.example.sampledatabasetest.manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PersonRequest extends DBRequest {

	int mMin;
	int mMax;
	
	Cursor cursor;
	
	public PersonRequest(int min, int max) {
		mMin = min;
		mMax = max;
	}
	
	@Override
	protected void processQuery(SQLiteDatabase db) {
		String[] columns = { DBConstant.PersonTable.ID, DBConstant.PersonTable.NAME, DBConstant.PersonTable.AGE };
		String selection = DBConstant.PersonTable.AGE + " > ? AND " + DBConstant.PersonTable.AGE + " < ?";
		String[] selectionArgs = { "" + mMin, "" + mMax };
		cursor = db.query(DBConstant.PersonTable.TABLE_NAME, 
				columns, selection, selectionArgs, 
				null, null, null);
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return cursor;
	}

}
