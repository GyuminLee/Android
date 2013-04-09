package com.example.sampledatabasetest.manager;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.example.sampledatabasetest.MyApplication;

public class DBManager {

	private static DBManager instance;
	
	private MyDatabaseOpenHelper mDBHelper;
	
	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}
	
	private DBManager() {
		mDBHelper = new MyDatabaseOpenHelper(MyApplication.getContext(), null, null, 0);
		
	}
	
	
	public void insertPerson(Person person) {
		ContentValues values = new ContentValues();
		values.put(DBConstant.PersonTable.NAME, person.name);
		values.put(DBConstant.PersonTable.AGE, person.age);
		
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		db.insert(DBConstant.PersonTable.TABLE_NAME, null, values);
		db.close();
	}
	
	public Cursor getPersonList(int minage, int maxage) {
		String[] columns = { DBConstant.PersonTable.ID, DBConstant.PersonTable.NAME, DBConstant.PersonTable.AGE };
		String selection = DBConstant.PersonTable.AGE + " > ? AND " + DBConstant.PersonTable.AGE + " < ?";
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		String[] selectionArgs = { "" + minage, "" + maxage };
		Cursor c = db.query(DBConstant.PersonTable.TABLE_NAME, 
				columns, selection, selectionArgs, 
				null, null, null);
		return c;
	}
	
	public List<Person> getListPerson(int minage, int maxage) {
		String[] columns = { DBConstant.PersonTable.ID, DBConstant.PersonTable.NAME, DBConstant.PersonTable.AGE };
		String selection = DBConstant.PersonTable.AGE + " > ? AND " + DBConstant.PersonTable.AGE + " < ?";
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		String[] selectionArgs = { "" + minage, "" + maxage };
		Cursor c = db.query(DBConstant.PersonTable.TABLE_NAME, 
				columns, selection, selectionArgs, 
				null, null, null);
		List<Person> list = new ArrayList<Person>();
		
		int nameIndex = c.getColumnIndex(DBConstant.PersonTable.NAME);
		int ageIndex = c.getColumnIndex(DBConstant.PersonTable.AGE);
		
		while(c.moveToNext()) {
			Person p = new Person();
			p.name = c.getString(nameIndex);
			p.age = c.getInt(ageIndex);
			list.add(p);
		}
		
		return list;
	}
	

	public void getAsyncDBProcess(DBRequest request, DBRequest.OnQueryCompleteListener listener, Handler handler) {
		request.setOnQueryCompleteListener(listener);
		request.setHandler(handler);
		new Thread(new ProcessRunnable(request)).start();
	}
	
	class ProcessRunnable implements Runnable {
		DBRequest request;
		
		public ProcessRunnable(DBRequest request) {
			this.request = request;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			SQLiteDatabase db = mDBHelper.getReadableDatabase();
			request.process(db);
		}
		
	}
	
	
}
