package org.tacademy.network.rss.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	final static int DB_VERSION = 1;
	
	public DatabaseHelper(Context context) {
		super(context, "NPRNewsDB", null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createItemTable = "CREATE TABLE " + DBScheme.ItemTable.TABLE_NAME +" (" +
			DBScheme.ItemTable.ID + " INTEGER PRIMARY KEY autoincrement , " +
			DBScheme.ItemTable.TITLE + " TEXT ," +
			DBScheme.ItemTable.DESCRIPTION + " TEXT ," +
			DBScheme.ItemTable.PUBLISH_DATE + " TEXT ," + 
			DBScheme.ItemTable.LINK + " TEXT, " +
			DBScheme.ItemTable.CONTENT + " TEXT," +
			DBScheme.ItemTable.CHANNEL_ID + " INTEGER );";
		db.execSQL(createItemTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
		// ...
	}
	
}
