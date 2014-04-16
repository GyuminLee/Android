package com.example.sample3videomediaplayer;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Video.Thumbnails;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class VideoListActivity extends ActionBarActivity implements LoaderCallbacks<Cursor> {

	ListView listView;
	SimpleCursorAdapter mAdapter;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.video_list_layout);
	    listView = (ListView)findViewById(R.id.listView1);
	    String[] from = { MediaStore.Video.Media.DISPLAY_NAME };
	    int[] to = {android.R.id.text1};
	    mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);
	    listView.setAdapter(mAdapter);
	    listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Uri uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
				Intent result = new Intent();
//				Bitmap bm = MediaStore.Video.Thumbnails.getThumbnail(getContentResolver(), id, Thumbnails.MICRO_KIND, null);				
				result.setData(uri);
				setResult(Activity.RESULT_OK, result);
			}
		});
	    
	    getSupportLoaderManager().initLoader(0, null, this);
	}
	@Override
	public Loader<Cursor> onCreateLoader(int code, Bundle b) {
		String[] projection = { MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME};
		
		return new CursorLoader(this, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
	}
	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor c) {
		mAdapter.swapCursor(c);
	}
	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);
	}

}
