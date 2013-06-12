package com.example.testcontentgetsample;

import android.content.ContentResolver;
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

public class MainActivity extends FragmentActivity implements LoaderCallbacks<Cursor>{

	EditText keywordView;
	ListView list;
	SimpleCursorAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		keywordView = (EditText)findViewById(R.id.keyword);
		list = (ListView)findViewById(R.id.listView1);
		Button btn = (Button)findViewById(R.id.search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				Bundle b = new Bundle();
				b.putString("keyword", keyword);
				getSupportLoaderManager().restartLoader(0, b, MainActivity.this);
			}
		});
		
		keywordView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				showList(s.toString());
				Bundle b = new Bundle();
				b.putString("keyword", s.toString());
				getSupportLoaderManager().restartLoader(0, b, MainActivity.this);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		String[] from = {ContactsContract.Contacts.DISPLAY_NAME};
		int[] to = {android.R.id.text1};
		mAdapter = new SimpleCursorAdapter(
				MainActivity.this, 
				android.R.layout.simple_list_item_1, 
				null, 
				from, 
				to, 
				0);
		list.setAdapter(mAdapter);
		
		getSupportLoaderManager().initLoader(0, null, this);
		
	}
	
	
	private void showList(String keyword) {
		new MyWorker().execute(keyword);
	}
	
	class MyWorker extends AsyncTask<String, Integer, Cursor> {

		@Override
		protected Cursor doInBackground(String... keywords) {
			String keyword = null;
			if (keywords != null && keywords.length > 0) {
				keyword = keywords[0];
			}
			ContentResolver resolver = getContentResolver();
			
			Uri contactUrl = ContactsContract.Contacts.CONTENT_URI;
			if (keyword != null && !keyword.equals("")) {
				contactUrl = Uri.withAppendedPath(
						ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(keyword));
			}
			String[] columns = { ContactsContract.Contacts._ID,
					ContactsContract.Contacts.DISPLAY_NAME };
			String selection = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
		            + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";
			String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
			
			Cursor c = resolver.query(contactUrl, columns, selection, null, sortOrder);
			return c;
		}
		
		@Override
		protected void onPostExecute(Cursor result) {
			startManagingCursor(result);
			mAdapter.swapCursor(result);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public Loader<Cursor> onCreateLoader(int code, Bundle args) {
		String keyword = null;
		if (args != null) {
			keyword = args.getString("keyword");
		}
		Uri contactUrl = ContactsContract.Contacts.CONTENT_URI;
		if (keyword != null && !keyword.equals("")) {
			contactUrl = Uri.withAppendedPath(
					ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(keyword));
		}
		String[] columns = { ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME };
		String selection = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
	            + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
				
		return new CursorLoader(this, contactUrl, columns, selection, null, sortOrder);
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
