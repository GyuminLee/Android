package com.example.sample2database;

import java.util.ArrayList;

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
	
	public void update(Person person) {
		SQLiteDatabase db = openHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBConstants.PersonTable.COLUMN_NAME, person.name);
		values.put(DBConstants.PersonTable.COLUMN_AGE, person.age);
		String selection = DBConstants.PersonTable._ID + " = ? ";
		String[] args = {"" + person.id};
		db.update(DBConstants.PersonTable.TABLE_NAME, values, selection, args);
		db.close();
	}

	public Cursor query(String[] columns) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		return db.query(DBConstants.PersonTable.TABLE_NAME, columns, null,
				null, null, null, null);
	}
	
	public void insertMulti(ArrayList<Person> personlist) {
		SQLiteDatabase db = openHelper.getWritableDatabase();
		db.beginTransaction();
		try {
			ContentValues values = new ContentValues();
			for (Person p : personlist) {
				values.clear();
				values.put(DBConstants.PersonTable.COLUMN_NAME, p.name);
				values.put(DBConstants.PersonTable.COLUMN_AGE, p.age);
				db.insert(DBConstants.PersonTable.TABLE_NAME, null, values);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		db.close();
	}

}
