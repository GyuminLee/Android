package com.example.sampledatabasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseOpenHelper extends SQLiteOpenHelper {

	public final static String DB_NAME = "MyDB";
	public final static int DB_VERSION = 1;
	
	public MyDatabaseOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		String sql = "CREATE TABLE " + 
					DBConstant.PersonTable.TABLE_NAME +"(" +
					DBConstant.PersonTable.ID + " integer primary key autoincrement," +
					DBConstant.PersonTable.NAME + " text," +
					DBConstant.PersonTable.AGE + " integer)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
		// TODO Auto-generated method stub
		String sql = "DROP TABLE " + DBConstant.PersonTable.TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

}
