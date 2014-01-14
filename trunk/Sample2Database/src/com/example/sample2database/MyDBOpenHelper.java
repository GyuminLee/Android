package com.example.sample2database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBOpenHelper extends SQLiteOpenHelper {

	private final static String DB_NAME = "mydb";
	private final static int DB_VERSION = 1;
	
	public MyDBOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE "+DBConstants.PersonTable.TABLE_NAME +"(" + 
					 DBConstants.PersonTable._ID + " integer PRIMARY KEY autoincrement, " +
					 DBConstants.PersonTable.COLUMN_NAME + " text," +
					 DBConstants.PersonTable.COLUMN_AGE + " integer);";
		db.execSQL(sql);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// ...
	}
}
