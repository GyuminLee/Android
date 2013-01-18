package com.example.testdatabasesample;

import android.R.array;
import android.animation.ArgbEvaluator;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.provider.BaseColumns;

public class DatabaseModel {

	private final static String DB_NAME = "mydata.db";
	private final static int DB_VERSION = 1;
	
	private static DatabaseModel instance;
	
	DatabaseHelper mDbHelper;
	
	public interface OnQueryResultListener {
		public void onQueryResult(Cursor c);
	}
	
	public static DatabaseModel getInstance() {
		if (instance == null) {
			instance = new DatabaseModel();
		}
		return instance;
	}
	
	private DatabaseModel() {
		Context context = MyApplication.getContext();
		mDbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
	}
	
	public DatabaseHelper getDBHelper() {
		return mDbHelper;
	}
	
	public void saveData(MyData data) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(DataFields.People.NAME, data.name);
		values.put(DataFields.People.PHONE, data.phone);
		values.put(DataFields.People.ADDRESS, data.address);
		values.put(DataFields.People.AGE, data.age);
		
		db.insert(DataFields.People.TABLE_NAME, null, values);
		
		db.close();
	}
	
	public void getDataList(String name, OnQueryResultListener listener, Handler handler) {
		String selection = null;
		String[] args = null;
		String[] projection = { DataFields.People.ID, 
				DataFields.People.NAME, 
				DataFields.People.PHONE, 
				DataFields.People.ADDRESS, 
				DataFields.People.AGE };
		
		if (name != null && !name.equals("")) {
			selection = DataFields.People.NAME + " = ?";
			args = new String[] {name};
		}
		new MyThread(DataFields.People.TABLE_NAME, 
				projection, 
				selection, 
				args, 
				null, 
				null, 
				null, 
				listener, 
				handler).start();
	}
	
	class MyThread extends Thread {
		String mTableName;
		String[] mProjection;
		String mSelection;
		String[] mArgs;
		String mGroupBy;
		String mHaving;
		String mOrderBy;
		OnQueryResultListener mListener;
		Handler mHandler;
		Cursor mCursor;
		
		public MyThread(String tableName, 
				String[] projection, 
				String selection, 
				String[] args,
				String groupby,
				String having,
				String orderby, 
				OnQueryResultListener listener, 
				Handler handler) {
			mTableName = tableName;
			mProjection = projection;
			mSelection = selection;
			mArgs = args;
			mGroupBy = groupby;
			mHaving = having;
			mOrderBy = orderby;
			mListener = listener;
			mHandler = handler;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			
			mCursor = db.query(mTableName, mProjection, mSelection, mArgs, mGroupBy, mHaving, mOrderBy);
			
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					mListener.onQueryResult(mCursor);
				}
			});
		}
	}
	
	public final static class DataFields {
		public final static class People {
			public final static String TABLE_NAME = "peopletbl";
			public final static String ID = BaseColumns._ID;
			public final static String NAME = "name";
			public final static String PHONE = "phone";
			public final static String ADDRESS = "address";
			public final static String AGE = "age";
		}
	}
	
	class DatabaseHelper extends SQLiteOpenHelper {


		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			String createTable = "CREATE TABLE "+DataFields.People.TABLE_NAME+" (" +
					DataFields.People.ID + " integer PRIMARY KEY autoincrement," +
					DataFields.People.NAME + " text," +
					DataFields.People.PHONE + " text," +
					DataFields.People.ADDRESS + " text," +
					DataFields.People.AGE + " integer);";
			db.execSQL(createTable);
		}
		

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			onCreate(db);
		}
	}
	
}
