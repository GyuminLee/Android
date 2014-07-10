package com.example.sample4cursorloader;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements LoaderCallbacks<Cursor> {

	EditText keywordView;
	ListView listView;
	SimpleCursorAdapter mAdapter;
	
	String[] projection = {Contacts._ID, Contacts.DISPLAY_NAME };
	String selection = Contacts.DISPLAY_NAME + " NOTNULL AND " +
					   Contacts.DISPLAY_NAME + " != ''";
	String[] selectionArgs = null;
	String sortOrder = Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		keywordView = (EditText)findViewById(R.id.editText1);
		listView = (ListView)findViewById(R.id.listView1);
		String[] from = { Contacts.DISPLAY_NAME };
		int[] to = { android.R.id.text1 };
		mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);
		listView.setAdapter(mAdapter);
		keywordView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String keyword = s.toString();
				Bundle b = new Bundle();
				b.putString(PARAM_KEYWORD, keyword);
				getSupportLoaderManager().restartLoader(0, b, MainActivity.this);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		getSupportLoaderManager().initLoader(0, null, this);
	}

	private static final String PARAM_KEYWORD = "keyword";
	@Override
	public Loader<Cursor> onCreateLoader(int code, Bundle args) {
		Uri uri = Contacts.CONTENT_URI;
		if (args != null) {
			String keyword = args.getString(PARAM_KEYWORD);
			if (keyword != null && !keyword.equals("")) {
				uri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, Uri.encode(keyword));
			}
		}		
		return new CursorLoader(this, uri, projection, selection, selectionArgs, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
	
}
