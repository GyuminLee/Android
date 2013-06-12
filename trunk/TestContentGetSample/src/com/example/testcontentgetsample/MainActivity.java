package com.example.testcontentgetsample;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	EditText keywordView;
	ListView list;
	
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
				showList(keyword);
			}
		});
		keywordView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				showList(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
	}
	
	
	private void showList(String keyword) {
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
		String[] from = {ContactsContract.Contacts.DISPLAY_NAME};
		int[] to = {android.R.id.text1};
		
		Cursor c = resolver.query(contactUrl, columns, selection, null, sortOrder);
		startManagingCursor(c);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				MainActivity.this, 
				android.R.layout.simple_list_item_1, 
				c, 
				from, 
				to, 
				0);
		list.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
