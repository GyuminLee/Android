package com.example.samplecontentprovidertest;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

	ListView list;
	SimpleCursorAdapter mAdapter;

	Uri peopleURI = ContactsContract.Contacts.CONTENT_URI;
	String[] projection = { ContactsContract.Contacts._ID, 
			ContactsContract.Contacts.DISPLAY_NAME};
	String selection = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
            + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";
	String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
	
	EditText keywordView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		keywordView = (EditText)findViewById(R.id.editText1);
	
		list = (ListView)findViewById(R.id.listView1);
		mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, 
				new String[] {ContactsContract.Contacts.DISPLAY_NAME}, 
				new int[] { android.R.id.text1 }, 
				0);
		list.setAdapter(mAdapter);

//		ContentResolver resolver = getContentResolver();
//		Cursor c = resolver.query(peopleURI, 
//				projection, 
//				selection, null, sortOrder);
//		startManagingCursor(c);
//		mAdapter.swapCursor(c);
		
		keywordView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
			
				String keyword = "" + s;
//				requery(keyword);
				Bundle args = new Bundle();
				args.putString("keyword", keyword);
				getSupportLoaderManager().restartLoader(0, args, MainActivity.this);
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

	public void requery(String keyword) {
		Uri uri;
		if (keyword != null && 
				!keyword.equals("")) {
			uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(keyword));
		} else {
			uri = peopleURI;
		}
		Cursor c = getContentResolver().query(uri, projection, selection, null, sortOrder);
		startManagingCursor(c);
		mAdapter.swapCursor(c);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int code, Bundle args) {
		// TODO Auto-generated method stub
		String keyword = null;
		if (args != null) {
			keyword = args.getString("keyword");
		}
		Uri uri;
		if (keyword != null && !keyword.equals("")) {
			uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(keyword));
		} else {
			uri = peopleURI;
		}
		
		return new CursorLoader(this, uri, projection, selection, null, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(c);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(null);
	}

}
