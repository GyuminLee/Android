package com.example.samplecalendarprovider;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Instances;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<Cursor>{

	SimpleCursorAdapter mAdapter;
	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		String[] from = {CalendarContract.Events.TITLE};
		int[] to = {android.R.id.text1};
		mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);
		listView.setAdapter(mAdapter);
		getSupportLoaderManager().initLoader(0, null, this);
	}

	String[] projection = {CalendarContract.Events._ID,CalendarContract.Events.TITLE};
    private static final String WHERE_CALENDARS_SELECTED = CalendarContract.Calendars.VISIBLE + "=?";
    private static final String[] WHERE_CALENDARS_ARGS = {
        "1"
    };
    private static final String DEFAULT_SORT_ORDER = "begin ASC";
    
	
	@Override
	public Loader<Cursor> onCreateLoader(int code, Bundle bundle) {
		long begin, end;
		Calendar c = new GregorianCalendar(Locale.getDefault());
//		c.set(Calendar.YEAR, 2014); // 2014
//		c.set(Calendar.MONTH, 6); // 7
//		c.set(Calendar.DAY_OF_MONTH, 25); // 25
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		begin = c.getTimeInMillis();
		c.add(Calendar.DAY_OF_YEAR, 1);
		end = c.getTimeInMillis();
		
		// Cursor 
//		Cursor cursor = Instances.query(getContentResolver(), projection, begin, end);
		
		// CursorLoader
		Uri.Builder builder = Instances.CONTENT_URI.buildUpon();
		ContentUris.appendId(builder, begin);
		ContentUris.appendId(builder, end);
		
		return new CursorLoader(this, builder.build(), projection, WHERE_CALENDARS_SELECTED, WHERE_CALENDARS_ARGS, DEFAULT_SORT_ORDER);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		mAdapter.swapCursor(arg1);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);
	}
}
