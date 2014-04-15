package com.example.sample3mediaplayer;

import java.util.HashMap;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class MusicListActivity extends ActionBarActivity implements
		LoaderCallbacks<Cursor> {

	/** Called when the activity is first created. */
	SimpleCursorAdapter mAdapter;
	ListView listView;

	int albumIdIndex = -1;

	public static final String PARAM_TITLE = "title";
	
	HashMap<Long, Bitmap> mMap = new HashMap<Long, Bitmap>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_list);
		listView = (ListView) findViewById(R.id.listView1);
		String[] from = { MediaStore.Audio.Media.DISPLAY_NAME,
				MediaStore.Audio.Media.ALBUM_ID };
		int[] to = { R.id.titleView, R.id.albumArt };
		mAdapter = new SimpleCursorAdapter(this, R.layout.item_layout, null,
				from, to, 0);
		mAdapter.setViewBinder(new ViewBinder() {

			@Override
			public boolean setViewValue(View view, Cursor cursor,
					int columnIndex) {
				if (albumIdIndex == columnIndex) {
					ImageView imageView = (ImageView) view;
					long id = cursor.getLong(columnIndex);
					Bitmap bitmap = mMap.get(id);
					if (bitmap == null) {
						String albumArt = null;
						String[] projection = { MediaStore.Audio.Albums.ALBUM_ART };
						String selection = MediaStore.Audio.Albums._ID + " = ?";
						String[] selectionArgs = { "" + id };
						Cursor c = getContentResolver().query(
								MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
								projection, selection, selectionArgs, null);
						if (c.moveToNext()) {
							albumArt = c.getString(c
									.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
						}
						if (albumArt == null) {
							bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
						} else {
							bitmap = BitmapFactory.decodeFile(albumArt);
						}
						mMap.put((Long)id, bitmap);
					}
					imageView.setImageBitmap(bitmap);
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
				Intent result = new Intent();
				Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
				result.setData(uri);
				Cursor c = (Cursor)listView.getItemAtPosition(position);
				String name = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
				result.putExtra(PARAM_TITLE, name);
				setResult(Activity.RESULT_OK, result);
				finish();		
			}
		});
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int code, Bundle b) {
		String[] projection = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ALBUM_ID};
		
		return new CursorLoader(this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
		albumIdIndex = c.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
		mAdapter.swapCursor(c);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);
	}

}
