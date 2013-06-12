package com.example.testdatabasesample2;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.example.testdatabasesample2.DBConstant.PersonTable;

public class DatabaseManager {

	Context mContext;
	
	DatabaseOpenHelper mHelper;
	
	public interface OnQueryCompleteListener {
		public void onCompleted(ArrayList<Person> personlist);
	}
	
	private static DatabaseManager instance;
	public static DatabaseManager getInstance() {
		if (instance == null) {
			instance = new DatabaseManager();
		}
		return instance;
	}
	
	private DatabaseManager() {
		mContext = MyApplication.getContext();
		mHelper = new DatabaseOpenHelper(mContext);
	}
	
	public void savePerson(Person person) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PersonTable.NAME, person.name);
		values.put(PersonTable.ADDRESS, person.address);
		db.insert(PersonTable.TABLE_NAME, null, values);
		db.close();
	}
	
	public void getPersonTable(
			final OnQueryCompleteListener listener,
			final Handler handler) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				final ArrayList<Person> list = new ArrayList<Person>();
				SQLiteDatabase db = mHelper.getReadableDatabase();
				String[] columns = {PersonTable.ID, PersonTable.NAME, PersonTable.ADDRESS};
				
				Cursor c = db.query(PersonTable.TABLE_NAME, columns, null, null, null, null, null);
				int indexID = c.getColumnIndex(PersonTable.ID);
				int indexName = c.getColumnIndex(PersonTable.NAME);
				int indexAddress = c.getColumnIndex(PersonTable.ADDRESS);
				
				while(c.moveToNext()) {
					Person p = new Person();
					p.id = c.getInt(indexID);
					p.name = c.getString(indexName);
					p.address = c.getString(indexAddress);
					list.add(p);
				}
				c.close();
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						listener.onCompleted(list);
					}
				});
			}
		}).start();
		
	}
}
