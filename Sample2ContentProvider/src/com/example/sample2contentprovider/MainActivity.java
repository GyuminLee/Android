package com.example.sample2contentprovider;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	EditText keywordView;
	ListView listView;
	SimpleCursorAdapter mAdapter;
	String[] columns = { ContactsContract.Contacts._ID,
			ContactsContract.Contacts.DISPLAY_NAME };
	String selection = "((" + ContactsContract.Contacts.DISPLAY_NAME
			+ " NOTNULL) AND (" + ContactsContract.Contacts.DISPLAY_NAME
			+ " != ''))";

	String orderby = " COLLATE LOCALIZED ASC";

	Cursor mCursor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		keywordView = (EditText) findViewById(R.id.keywordView);
		listView = (ListView) findViewById(R.id.listView1);
		mCursor = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, columns, selection,
				null, orderby);
		mAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, mCursor,
				new String[] { ContactsContract.Contacts.DISPLAY_NAME },
				new int[] { android.R.id.text1 }, 0);
		listView.setAdapter(mAdapter);

		Button btn = (Button) findViewById(R.id.btnSearch);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				searchPhoneBook(keyword);
			}
		});
	}

	private void searchPhoneBook(String keyword) {
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		if (keyword != null && !keyword.equals("")) {
			uri = Uri.withAppendedPath(
					ContactsContract.Contacts.CONTENT_FILTER_URI,
					Uri.encode(keyword));
		}
		mCursor = getContentResolver().query(uri, columns, selection, null, orderby);
		mAdapter.swapCursor(mCursor);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mCursor != null) {
			mCursor.close();
			mCursor = null;
		}
	}

}
