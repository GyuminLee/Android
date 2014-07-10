package com.example.sample4contentprovider;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView listView;
	SimpleCursorAdapter mAdapter;
	EditText keywordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		keywordView = (EditText)findViewById(R.id.editText1);
		String[] from = { Contacts.DISPLAY_NAME };
		int[] to = { android.R.id.text1 };
		mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);
		listView.setAdapter(mAdapter);
		
		keywordView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String keyword = s.toString();
				if (keyword != null && !keyword.equals("")) {
					searchContacts(keyword);
				} else {
					initData();
				}
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
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					searchContacts(keyword);
				} else {
					initData();
				}
			}
		});
		
		initData();
	}
	
	String[] projection = {Contacts._ID, Contacts.DISPLAY_NAME };
	String selection = Contacts.DISPLAY_NAME + " NOTNULL AND " +
					   Contacts.DISPLAY_NAME + " != ''";
	String[] selectionArgs = null;
	String sortOrder = Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
	
	Cursor mCursor;
	private void initData() {
		ContentResolver resolver = getContentResolver();
		
		mCursor = resolver.query(Contacts.CONTENT_URI, projection, selection, selectionArgs, sortOrder);
		mAdapter.swapCursor(mCursor);
	}
	
	private void searchContacts(String keyword) {
		Uri uri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, Uri.encode(keyword));
		ContentResolver resolver = getContentResolver();
		
		mCursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
		mAdapter.swapCursor(mCursor);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mCursor != null) {
			mCursor.close();
		}
	}
}
