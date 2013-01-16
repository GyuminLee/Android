package com.example.samplecursorloader;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

	Cursor mCursor;
	ListView list;
	android.widget.SimpleCursorAdapter mAdapter;
	SimpleCursorAdapter mCursorAdapter;
	EditText keywordView;
	String keyword = "";
	
	Uri peopleURI = ContactsContract.Contacts.CONTENT_URI;
	String[] projection = { ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME };
	String selection = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
            + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";
	String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		list = (ListView)findViewById(R.id.listView1);
		keywordView = (EditText)findViewById(R.id.editText1);
		keywordView.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				keyword = "" + arg0;
				getSupportLoaderManager().restartLoader(0, null, MainActivity.this);
			}
			
		});
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				keyword = keywordView.getText().toString();
				getSupportLoaderManager().restartLoader(0, null, MainActivity.this);
			}
		});
//		oldLoading();
		newLoading();
	}
	
	void newLoading() {
		mCursorAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, 
				null, 
				new String[] { ContactsContract.Contacts.DISPLAY_NAME }, 
				new int[] { android.R.id.text1 },
				0);
		list.setAdapter(mCursorAdapter);
		getSupportLoaderManager().initLoader(0, null, this);
	}
	
	void oldLoading() {
		mAdapter = new android.widget.SimpleCursorAdapter(MainActivity.this, 
				android.R.layout.simple_list_item_1, 
				null, 
				new String[] { ContactsContract.Contacts.DISPLAY_NAME }, 
				new int[] { android.R.id.text1 });
		list.setAdapter(mAdapter);
		initCursorAdapter();
	}

	void initCursorAdapter() {
		mCursor = getContentResolver().query(peopleURI, projection, selection, null, sortOrder);
		mAdapter.changeCursor(mCursor);
		startManagingCursor(mCursor);
//		new MyTask().execute();
	}
	
	class MyTask extends AsyncTask<String, Integer, Cursor> {

		@Override
		protected Cursor doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			return getContentResolver().query(peopleURI, projection, selection, null, sortOrder);
		}
		
		@Override
		protected void onPostExecute(Cursor result) {
			// TODO Auto-generated method stub
			mCursor = result;
			mAdapter.changeCursor(mCursor);
			startManagingCursor(mCursor);
		}
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopManagingCursor(mCursor);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		Uri baseUri;
		if (keyword != null && !keyword.equals("")) {
			baseUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI,Uri.encode(keyword));
		} else {
			baseUri = peopleURI;
		}
		return new CursorLoader(this, baseUri, projection, selection, null, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub
		mCursorAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		mCursorAdapter.swapCursor(null);
	}

}
