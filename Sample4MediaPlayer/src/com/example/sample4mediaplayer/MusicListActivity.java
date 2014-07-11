package com.example.sample4mediaplayer;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MusicListActivity extends ActionBarActivity implements LoaderCallbacks<Cursor>{

	ListView listView;
	SimpleCursorAdapter mAdapter;
	public static final String PARAM_DISPLAY_NAME = "display";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.list_layout);
	    listView = (ListView)findViewById(R.id.listView1);
	    String[] from = {MediaStore.Audio.Media.DISPLAY_NAME};
	    int[] to = {android.R.id.text1};
	    mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);
	    listView.setAdapter(mAdapter);
	    listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Cursor c = (Cursor)listView.getItemAtPosition(position);
				String display = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
				Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
				Intent data = new Intent();
				data.setData(uri);
				data.putExtra(PARAM_DISPLAY_NAME, display);
				setResult(Activity.RESULT_OK, data);
				finish();
			}
		});
	    getSupportLoaderManager().initLoader(0, null, this);
	}
	
	String[] projection = { MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME};
	@Override
	public Loader<Cursor> onCreateLoader(int code, Bundle b) {	
		return new CursorLoader(this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
	}
	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		mAdapter.swapCursor(arg1);
	}
	
	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);
	}

}
