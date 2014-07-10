package com.example.sample4contentprovider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.QuickContactBadge;

public class MainActivity extends Activity {

	ListView listView;
	SimpleCursorAdapter mAdapter;
	EditText keywordView;
	
	private int idColumnIndex = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		keywordView = (EditText)findViewById(R.id.editText1);
		String[] from = { Contacts._ID, Contacts.DISPLAY_NAME };
		int[] to = { R.id.photo, R.id.name_view };
		mAdapter = new SimpleCursorAdapter(this, R.layout.contacts_item_layout, null, from, to, 0);
		mAdapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor c, int columnIndex) {
				if (idColumnIndex == columnIndex) {
					
					QuickContactBadge badge = (QuickContactBadge)view;
					long id = c.getLong(columnIndex);
					Uri uri = ContentUris.withAppendedId(Contacts.CONTENT_URI, id);
					badge.assignContactUri(uri);
					return true;
				}
				return false;
			}
		});
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Uri uri = ContentUris.withAppendedId(Contacts.CONTENT_URI, id);
				
				
			}
		});
		
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
		idColumnIndex = mCursor.getColumnIndex(Contacts._ID);
		mAdapter.swapCursor(mCursor);
	}
	
	private void searchContacts(String keyword) {
		Uri uri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, Uri.encode(keyword));
		ContentResolver resolver = getContentResolver();
		
		mCursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
		idColumnIndex = mCursor.getColumnIndex(Contacts._ID);
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
