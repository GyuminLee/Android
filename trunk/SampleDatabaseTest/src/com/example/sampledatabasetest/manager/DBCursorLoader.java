package com.example.sampledatabasetest.manager;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

public abstract class DBCursorLoader extends AsyncTaskLoader<Cursor> {

	ForceLoadContentObserver mObserver;
	Cursor mCursor;
	
	public DBCursorLoader(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Cursor loadInBackground() {
        Cursor cursor = query();
        if (cursor != null) {
            // Ensure the cursor window is filled
            cursor.getCount();
            registerContentObserver(cursor, mObserver);
        }
        return cursor;
	}
	
	abstract protected Cursor query();

	protected void registerContentObserver(Cursor cursor, ContentObserver observer) {
		cursor.registerContentObserver(mObserver);
	}
	
	@Override
	public void deliverResult(Cursor cursor) {
		// TODO Auto-generated method stub
        if (isReset()) {
            // An async query came in while the loader is stopped
            if (cursor != null) {
                cursor.close();
            }
            return;
        }
        Cursor oldCursor = mCursor;
        mCursor = cursor;

        if (isStarted()) {
            super.deliverResult(cursor);
        }

        if (oldCursor != null && oldCursor != cursor && !oldCursor.isClosed()) {
            oldCursor.close();
        }
	}
	
	@Override
	protected void onStartLoading() {
		// TODO Auto-generated method stub
        if (mCursor != null) {
            deliverResult(mCursor);
        }
        if (takeContentChanged() || mCursor == null) {
            forceLoad();
        }
	}
	
	@Override
	protected void onStopLoading() {
		// TODO Auto-generated method stub
        cancelLoad();
	}
	
	@Override
	public void onCanceled(Cursor cursor) {
		// TODO Auto-generated method stub
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
	}
	
    @Override
    protected void onReset() {
        super.onReset();
        
        // Ensure the loader is stopped
        onStopLoading();

        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        mCursor = null;
    }
	
}
