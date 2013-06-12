package com.example.testdatabasesample2;

import com.example.testdatabasesample2.DBConstant.PersonTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "mydatabase";
	public static final int DB_VERSION = 1;
	
	public DatabaseOpenHelper(Context context) {
		super(context,DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTableQuery = "CREATE TABLE " + 
								  PersonTable.TABLE_NAME + "(" +
								  PersonTable.ID +" integer PRIMARY KEY autoincrement," +
								  PersonTable.NAME + " text, " +
								  PersonTable.ADDRESS + " text" +
								  ");";
		db.execSQL(createTableQuery);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE persontbl");
		onCreate(db);
	}
}
