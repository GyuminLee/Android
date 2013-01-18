package com.example.testdatabasesample;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;

public class MyDBLoader extends AsyncTaskLoader<Cursor> {

	String mTableName;
	String[] mProjection;
	String mSelection;
	String[] mArgs;
	String mGroupBy;
	String mHaving;
	String mOrderBy;

	
	public MyDBLoader(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void setParameter(String tableName, 
			String[] projection, 
			String selection, 
			String[] args, 
			String groupBy,
			String having,
			String orderBy) {
		mTableName = tableName;
		mProjection = projection;
		mSelection = selection;
		mArgs = args;
		mGroupBy = groupBy;
		mHaving = having;
		mOrderBy = orderBy;
	}

	@Override
	public Cursor loadInBackground() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = DatabaseModel.getInstance().getDBHelper().getReadableDatabase();
		Cursor c = db.query(mTableName, mProjection, mSelection, mArgs, mGroupBy, mHaving, mOrderBy);
		return c;
	}
	
	@Override
	protected void onStartLoading() {
		// TODO Auto-generated method stub
		forceLoad();
	}

	@Override
	public void deliverResult(Cursor data) {
		// TODO Auto-generated method stub
		super.deliverResult(data);
	}
}
