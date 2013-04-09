package com.example.sampledatabasetest.manager;

import android.content.Context;
import android.database.Cursor;

public class PersonCursorLoader extends DBCursorLoader {

	int mMin, mMax;
	
	public PersonCursorLoader(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public PersonCursorLoader(Context context, int min, int max) {
		super(context);
		mMin = min;
		mMax = max;
	}

	@Override
	protected Cursor query() {
		// TODO Auto-generated method stub
		return DBManager.getInstance().getPersonList(mMin, mMax);
	}

}
