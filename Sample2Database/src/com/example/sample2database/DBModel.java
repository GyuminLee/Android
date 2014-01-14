package com.example.sample2database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBModel {
	private static DBModel instance;

	public static DBModel getInstance() {
		if (instance == null) {
			instance = new DBModel();
		}
		return instance;
	}

	MyDBOpenHelper openHelper;

	private DBModel() {
		openHelper = new MyDBOpenHelper(MyApplication.getContext());
	}

	public void insert(Person person) {
		SQLiteDatabase db = openHelper.getWritableDatabase();
		// String insertSQL = "INSERT INTO " +
		// DBConstants.PersonTable.TABLE_NAME
		// + "(" + DBConstants.PersonTable.COLUMN_NAME + ","
		// + DBConstants.PersonTable.COLUMN_AGE + ") values('"
		// + person.name + "'," + person.age + ");";
		// db.execSQL(insertSQL);

		ContentValues values = new ContentValues();
		values.put(DBConstants.PersonTable.COLUMN_NAME, person.name);
		values.put(DBConstants.PersonTable.COLUMN_AGE, person.age);
		person.id = db.insert(DBConstants.PersonTable.TABLE_NAME, null, values);
		db.close();
	}

	public Cursor query(String[] columns) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		return db.query(DBConstants.PersonTable.TABLE_NAME, columns, null,
				null, null, null, null);
	}

}
