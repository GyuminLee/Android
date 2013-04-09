package com.example.sampledatabasetest.manager;

import com.example.sampledatabasetest.MyApplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

	private static DBManager instance;
	
	private MyDatabaseOpenHelper mDBHelper;
	
	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}
	
	private DBManager() {
		mDBHelper = new MyDatabaseOpenHelper(MyApplication.getContext(), null, null, 0);
		
	}
	
	
	public void insertPerson(Person person) {
		ContentValues values = new ContentValues();
		values.put(DBConstant.PersonTable.NAME, person.name);
		values.put(DBConstant.PersonTable.AGE, person.age);
		
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		db.insert(DBConstant.PersonTable.TABLE_NAME, null, values);
		db.close();
	}
	
	public Cursor getPersonList(int minage, int maxage) {
		String[] columns = { DBConstant.PersonTable.ID, DBConstant.PersonTable.NAME, DBConstant.PersonTable.AGE };
		String selection = DBConstant.PersonTable.AGE + " > ? AND " + DBConstant.PersonTable.AGE + " < ?";
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		String[] selectionArgs = { "" + minage, "" + maxage };
		Cursor c = db.query(DBConstant.PersonTable.TABLE_NAME, 
				columns, selection, selectionArgs, 
				null, null, null);
		return c;
	}
}
