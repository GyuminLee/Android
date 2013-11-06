package com.example.hellomediaplayertest;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MusicListActivity extends FragmentActivity 
	implements LoaderCallbacks<Cursor> {

	ListView listView;
	SimpleCursorAdapter mAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.music_list);
	    listView = (ListView)findViewById(R.id.listView1);
	    String[] from = { MediaStore.Audio.Media.TITLE };
	    int[] to = {android.R.id.text1 };
	    mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);
	    listView.setAdapter(mAdapter);
	    listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, arg3);
				Intent result = new Intent();
				result.setData(uri);
				setResult(RESULT_OK,result);
				finish();
			}
		});
	    getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		String[] projection = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE};
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
