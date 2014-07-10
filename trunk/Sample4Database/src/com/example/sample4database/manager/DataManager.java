package com.example.sample4database.manager;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sample4database.MyApplication;
import com.example.sample4database.entity.Person;

public class DataManager implements DBConstant.PersonTable {

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
	
	public Cursor getPersonCursor() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] columns = new String[] { _ID, FIELD_NAME, FIELD_AGE };
		String selection = null; //FIELD_AGE + " > ? AND " + FIELD_AGE + " < ?";
		String[] selectionArgs = null; //new String[] {"20" , "40"};
		String groupBy = null;
		String having = null;
		String orderBy = null;
		Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
		return c;
	}
	
	public ArrayList<Person> getPersonList() {
		ArrayList<Person> list = new ArrayList<Person>();
//		String sql = "SELECT "+DBConstant.PersonTable.FIELD_NAME+", "+DBConstant.PersonTable.FIELD_AGE+" FROM "+DBConstant.PersonTable.TABLE_NAME;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
//		Cursor c = db.rawQuery(sql, null);
		String[] columns = new String[] { _ID, FIELD_NAME, FIELD_AGE };
		String selection = FIELD_AGE + " > ? AND " + FIELD_AGE + " < ?";
		String[] selectionArgs = new String[] {"20" , "40"};
		String groupBy = null;
		String having = null;
		String orderBy = null;
		Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
		while(c.moveToNext()) {
			Person p = new Person();
			p._id = c.getLong(c.getColumnIndex(_ID));
			p.name = c.getString(c.getColumnIndex(FIELD_NAME));
			p.age = c.getInt(c.getColumnIndex(FIELD_AGE));
			list.add(p);
		}
		c.close();
		return list;
	}
	
	public void addPerson(Person p) {
//		String sql = "INSERT INTO "+DBConstant.PersonTable.TABLE_NAME+"("+DBConstant.PersonTable.FIELD_NAME+","+DBConstant.PersonTable.FIELD_AGE+") values('"+p.name+"'," + p.age + ");";
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(FIELD_NAME, p.name);
		values.put(FIELD_AGE, p.age);
		db.insert(TABLE_NAME, null, values);		
//		db.execSQL(sql);
		db.close();
	}
	
	public void updatePerson(Person p) {
		if (p._id != -1) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(FIELD_NAME, p.name);
			values.put(FIELD_AGE, p.age);
			String whereClause = _ID + " = ?";
			String[] whereArgs = new String[] {"" + p._id};
			db.update(TABLE_NAME, values, whereClause, whereArgs);
		} else {
			addPerson(p);
		}
	}
	
	public void deletePerson(Person p) {
		if (p._id != -1) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			String whereClause = _ID + " = ?";
			String[] whereArgs = new String[] {"" + p._id};
			db.delete(TABLE_NAME, whereClause, whereArgs);
		}
	}
}
