package com.example.sample4database.manager;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sample4database.MyApplication;
import com.example.sample4database.entity.Person;

public class DataManager {

	private static DataManager instance;
	public static DataManager getInstance() {
		if (instance == null) {
			instance = new DataManager();
		}
		return instance;
	}
	
	DBOpenHelper dbHelper;
	
	private DataManager() {
		dbHelper = new DBOpenHelper(MyApplication.getContext());
	}
	
	public ArrayList<Person> getPersonList() {
		ArrayList<Person> list = new ArrayList<Person>();
		String sql = "SELECT name, age FROM persontbl";
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery(sql, null);
		while(c.moveToNext()) {
			Person p = new Person();
			p.name = c.getString(c.getColumnIndex("name"));
			p.age = c.getInt(c.getColumnIndex("age"));
			list.add(p);
		}
		c.close();
		return list;
	}
	
	public void addPerson(Person p) {
		String sql = "INSERT INTO persontbl(name,age) values('"+p.name+"'," + p.age + ");";
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL(sql);
		db.close();
	}
}
