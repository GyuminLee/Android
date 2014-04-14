package com.example.sample3database;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

		String sql = "insert into " + DBConstant.PersonTable.TABLE_NAME + "("
				+ DBConstant.PersonTable.NAME + ","
				+ DBConstant.PersonTable.AGE + ") values('" + person.name
				+ "'," + person.age + ");";
		SQLiteDatabase db = openHelper.getWritableDatabase();
		db.execSQL(sql);
		db.close();
	}

	public ArrayList<Person> getPersonList() {
		ArrayList<Person> list = new ArrayList<Person>();
		String sql = "select " + DBConstant.PersonTable.NAME + ","
				+ DBConstant.PersonTable.AGE + " from "
				+ DBConstant.PersonTable.TABLE_NAME;
		SQLiteDatabase db = openHelper.getReadableDatabase();
		Cursor c = db.rawQuery(sql, null);
		while (c.moveToNext()) {
			Person p = new Person();
			p.name = c.getString(c.getColumnIndex(DBConstant.PersonTable.NAME));
			p.age = c.getInt(c.getColumnIndex(DBConstant.PersonTable.AGE));
			list.add(p);
		}
		return list;
	}
}
