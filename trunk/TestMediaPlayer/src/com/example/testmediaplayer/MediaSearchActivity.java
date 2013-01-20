package com.example.testmediaplayer;

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

public class MediaSearchActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

	ListView list;
	
	public static final String RESULT_MEDIA_URI = "mediauri";
	
	SimpleCursorAdapter mAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.search_layout);
	    
	    list = (ListView)findViewById(R.id.listView1);
	    
	    // TODO Auto-generated method stub
	    
	    mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, 
	    		new String[] { MediaStore.Audio.Media.TITLE }
	    		, new int[] {android.R.id.text1 }, 0);
	    
	    list.setAdapter(mAdapter);
	    list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
				Intent data = new Intent();
				data.setData(uri);
				setResult(RESULT_OK, data);
				finish();
			}
		});
	    getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		String[] projection = { MediaStore.Audio.Media._ID, 
				MediaStore.Audio.Media.TITLE, 
				MediaStore.Audio.Media.ALBUM_ID};
		
		return new CursorLoader(this, uri, projection , null, null, null);
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
