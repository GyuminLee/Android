package com.example.sample3contacts;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements LoaderCallbacks<Cursor> {

		public PlaceholderFragment() {
		}

		ListView listView;
		EditText keywordView;

		SimpleCursorAdapter mAdapter;
		String[] projection = { Contacts._ID, Contacts.DISPLAY_NAME };
		String selection = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("
				+ Contacts.HAS_PHONE_NUMBER + "=1) AND ("
				+ Contacts.DISPLAY_NAME + " != '' ))";
		String sortOrder = Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
		public static final String PARAM_KEYWORD = "keyword";
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			listView = (ListView) rootView.findViewById(R.id.listView1);
			keywordView = (EditText) rootView.findViewById(R.id.keywordView);
			Button btn = (Button) rootView.findViewById(R.id.btnSearch);
			btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					String keyword = keywordView.getText().toString();
					Uri uri = ContactsContract.Contacts.CONTENT_URI;
					if (keyword != null && !keyword.equals("")) {
						uri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, Uri.encode(keyword));
					}
					Cursor c = getActivity().getContentResolver().query(uri, projection, selection, null, sortOrder);
					mAdapter.swapCursor(c);
				}
			});
			keywordView.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					String keyword = s.toString();
					Bundle b = new Bundle();
					b.putString(PARAM_KEYWORD, keyword);
					((ActionBarActivity)getActivity()).getSupportLoaderManager().restartLoader(0, b, PlaceholderFragment.this);
//					Uri uri = ContactsContract.Contacts.CONTENT_URI;
//					if (keyword != null && !keyword.equals("")) {
//						uri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, Uri.encode(keyword));
//					}
//					Cursor c = getActivity().getContentResolver().query(uri, projection, selection, null, sortOrder);
//					mAdapter.swapCursor(c);
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

//			Cursor c = getActivity().getContentResolver().query(
//					ContactsContract.Contacts.CONTENT_URI, projection,
//					selection, null, sortOrder);
			String[] from = { Contacts.DISPLAY_NAME };
			int[] to = { android.R.id.text1 };
			mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, null, from, to, 0);
			listView.setAdapter(mAdapter);
			((ActionBarActivity)getActivity()).getSupportLoaderManager().initLoader(0, null, this);
			return rootView;
		}

		@Override
		public Loader<Cursor> onCreateLoader(int code, Bundle args) {
			
			Uri uri = ContactsContract.Contacts.CONTENT_URI;
			if (args != null) {
				String keyword = args.getString(PARAM_KEYWORD);
				if (keyword != null && !keyword.equals("")) {
					uri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, Uri.encode(keyword));
				}
			}
			return new CursorLoader(getActivity(), uri, projection, selection, null, sortOrder);
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

}
