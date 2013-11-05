package com.example.hellodatabasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "mydb.db";
	public static final int DB_VERSION = 1;

	public DBOpenHelper(Context context) {
		super(context,DB_NAME,null,DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE "+DBConstant.TableMessage.TABLE_NAME+"("+
					DBConstant.TableMessage._ID + " integer PRIMARY KEY autoincrement," +
					DBConstant.TableMessage.FIELD_MESSAGE+" text," +
					DBConstant.TableMessage.FIELD_TYPE+" integer," +
					DBConstant.TableMessage.FIELD_DATE+" text);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE tblMessage";
		db.execSQL(sql);
		onCreate(db);
	}

}
