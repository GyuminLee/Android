package com.example.sample2contentprovider;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

	EditText keywordView;
	ListView listView;
	SimpleCursorAdapter mAdapter;
	String[] columns = { ContactsContract.Contacts._ID,
			ContactsContract.Contacts.DISPLAY_NAME };
	String selection = "((" + ContactsContract.Contacts.DISPLAY_NAME
			+ " NOTNULL) AND (" + ContactsContract.Contacts.DISPLAY_NAME
			+ " != ''))";

	String orderby = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

	Cursor mCursor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		keywordView = (EditText) findViewById(R.id.keywordView);
		listView = (ListView) findViewById(R.id.listView1);
		mAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, null,
				new String[] { ContactsContract.Contacts.DISPLAY_NAME },
				new int[] { android.R.id.text1 }, 0);
		listView.setAdapter(mAdapter);

		keywordView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String keyword = s.toString();
				searchPhoneBook(keyword);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {				
			}
		});
		Button btn = (Button) findViewById(R.id.btnSearch);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				searchPhoneBook(keyword);
			}
		});
		
		getSupportLoaderManager().initLoader(0, null, this);
	}

	private void searchPhoneBook(String keyword) {
		Bundle b = new Bundle();
		b.putString("keyword", keyword);
		getSupportLoaderManager().restartLoader(0, b, this);
		
//		Uri uri = ContactsContract.Contacts.CONTENT_URI;
//		if (keyword != null && !keyword.equals("")) {
//			uri = Uri.withAppendedPath(
//					ContactsContract.Contacts.CONTENT_FILTER_URI,
//					Uri.encode(keyword));
//		}
//		mCursor = getContentResolver().query(uri, columns, selection, null, orderby);
//		mAdapter.swapCursor(mCursor);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int code, Bundle b) {
		String keyword = null;
		if (b != null) {
			keyword = b.getString("keyword");
		}
		
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		if (keyword != null && !keyword.equals("")) {
			uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(keyword));
		}
		
		return new CursorLoader(this, uri, columns, selection, null, orderby);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
		mAdapter.swapCursor(c);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

}
