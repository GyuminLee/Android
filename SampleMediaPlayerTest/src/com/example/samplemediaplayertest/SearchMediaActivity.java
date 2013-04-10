package com.example.samplemediaplayertest;

import android.app.Activity;
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

public class SearchMediaActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

	ListView list;
	SimpleCursorAdapter mAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    setContentView(R.layout.search_layout);
	    list = (ListView)findViewById(R.id.listView1);
	    mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, 
	    			new String[] {MediaStore.Audio.Media.TITLE}, new int[] {android.R.id.text1}, 0);
	    list.setAdapter(mAdapter);
	    list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				// TODO Auto-generated method stub
				Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
				Intent intent = new Intent();
				intent.setData(uri);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});
	    getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return new CursorLoader(this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 
				new String[] { MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE } , null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(arg1);
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(null);
	}

}
