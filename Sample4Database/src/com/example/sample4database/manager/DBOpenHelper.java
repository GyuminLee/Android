package com.example.sample4database.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "mydb";
	public static final int DB_VERSION = 1;
	public DBOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE persontbl(" +
					"_id integer PRIMARY KEY autoincrement, " +
				    "name text," +
					"age integer);";
		db.execSQL(sql);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		switch(oldVersion) {
		case 1:
			// ..
		case 2:
			// ...
		}
	}
} 
