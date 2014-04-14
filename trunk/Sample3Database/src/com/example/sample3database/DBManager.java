package com.example.sample3database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sample3database.DBConstant.PersonTable;

public class DBManager {
	private static DBManager instance;

	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	MyDBOpenHelper openHelper;

	private DBManager() {
		openHelper = new MyDBOpenHelper(MyApplication.getContext());
	}

	public void insertPerson(Person person) {

//		String sql = "insert into " + PersonTable.TABLE_NAME + "("
//				+ PersonTable.NAME + ","
//				+ PersonTable.AGE + ") values('" + person.name
//				+ "'," + person.age + ");";
		SQLiteDatabase db = openHelper.getWritableDatabase();
//		db.execSQL(sql);
		ContentValues values = new ContentValues();
		values.put(PersonTable.NAME, person.name);
		values.put(PersonTable.AGE, person.age);
		
		db.insert(PersonTable.TABLE_NAME, null, values);
		db.close();
	}

	public void updatePerson(Person person) {
		if (person._id != -1) {
			SQLiteDatabase db = openHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(PersonTable.NAME, person.name);
			values.put(PersonTable.AGE, person.age);
			String whereClause = PersonTable._ID + " = ?";
			String[] whereArgs = {"" + person._id};			
			db.update(PersonTable.TABLE_NAME, values, whereClause, whereArgs);
			db.close();
		} else {
			insertPerson(person);
		}
	}
	
	public void deletePerson(Person person) {
		if (person._id != -1) {
			SQLiteDatabase db = openHelper.getWritableDatabase();
			String whereClause = PersonTable._ID + " = ?";
			String[] whereArgs = {"" + person._id};			
			db.delete(PersonTable.TABLE_NAME, whereClause, whereArgs);
			db.close();
		}
	}
	
	public ArrayList<Person> getPersonList() {
		ArrayList<Person> list = new ArrayList<Person>();
		String sql = "select " + PersonTable._ID + ","+PersonTable.NAME + ","
				+ PersonTable.AGE + " from "
				+ PersonTable.TABLE_NAME;
		SQLiteDatabase db = openHelper.getReadableDatabase();
		Cursor c = db.rawQuery(sql, null);

//		String[] columns = {PersonTable._ID, PersonTable.NAME, PersonTable.AGE};
//		String selection = PersonTable.AGE + "> ? and " + PersonTable.AGE + " < ?";
//		String[] selectionArgs = {"20" , "40"};
//		Cursor c2 = db.query(PersonTable.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
		
		while (c.moveToNext()) {
			Person p = new Person();
			p._id = c.getLong(c.getColumnIndex(PersonTable._ID));
			p.name = c.getString(c.getColumnIndex(DBConstant.PersonTable.NAME));
			p.age = c.getInt(c.getColumnIndex(DBConstant.PersonTable.AGE));
			list.add(p);
		}
		c.close();
		return list;
	}
	
	public Cursor getPersonCursor() {
		String sql = "select " + PersonTable._ID + ","+PersonTable.NAME + ","
				+ PersonTable.AGE + " from "
				+ PersonTable.TABLE_NAME;
		SQLiteDatabase db = openHelper.getReadableDatabase();
		Cursor c = db.rawQuery(sql, null);
		return c;
	}
	
	public void insertTransaction(ArrayList<Person> list) {
		SQLiteDatabase db = openHelper.getWritableDatabase();
		try {
			db.beginTransaction();
			ContentValues values = new ContentValues();
			for (Person p : list) {
				values.clear();
				values.put(PersonTable.NAME, p.name);
				values.put(PersonTable.AGE, p.age);
				db.insert(PersonTable.TABLE_NAME, null, values);
			}
			db.setTransactionSuccessful();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}
}
