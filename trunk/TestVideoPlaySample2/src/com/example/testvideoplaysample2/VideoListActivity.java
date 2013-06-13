package com.example.testvideoplaysample2;

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

public class VideoListActivity extends FragmentActivity implements LoaderCallbacks<Cursor>{

	ListView list;
	SimpleCursorAdapter mAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.video_list);
	    
	    list = (ListView)findViewById(R.id.listView1);
	    String[] from = {MediaStore.Video.Media.TITLE};
	    int[] to = {android.R.id.text1};
	    
	    mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);
	    list.setAdapter(mAdapter);
	    list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Uri contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
				Cursor c = (Cursor)mAdapter.getItem(position);
				int titleIndex = c.getColumnIndex(MediaStore.Video.Media.TITLE);
				String title = c.getString(titleIndex);
				Intent i = new Intent();
				i.setData(contentUri);
				i.putExtra("title", title);
				setResult(Activity.RESULT_OK, i);
				finish();
			}
		});
	    getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		String[] columns = {MediaStore.Video.Media._ID, 
				MediaStore.Video.Media.TITLE,
				MediaStore.Video.Media.WIDTH,
				MediaStore.Video.Media.HEIGHT,
				MediaStore.Video.Media.DATA};
		String selection = "((" + MediaStore.Video.Media.TITLE + " NOTNULL) AND ("
	            + MediaStore.Video.Media.TITLE + " != '' ))";
		String sortOrder = MediaStore.Video.Media.TITLE + " COLLATE LOCALIZED ASC";
		
		
		return new CursorLoader(this, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, selection, null, sortOrder);
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
