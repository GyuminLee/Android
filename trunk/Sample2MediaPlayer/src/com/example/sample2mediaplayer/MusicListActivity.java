package com.example.sample2mediaplayer;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class MusicListActivity extends FragmentActivity implements
		LoaderCallbacks<Cursor> {
	ListView listView;
	SimpleCursorAdapter mAdapter;

	public static final String PARAM_SELECT_AUDIO_TITLE = "audiotitle";

	final static int NOT_FIXED = -1;
	int albumIdIndex = NOT_FIXED;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_list);
		listView = (ListView) findViewById(R.id.listView1);
		mAdapter = new SimpleCursorAdapter(this, R.layout.item_layout, null,
				new String[] { MediaStore.Audio.Media.ALBUM_ID,
						MediaStore.Audio.Media.TITLE }, new int[] {
						R.id.imageView1, R.id.textView1 }, 0);
		mAdapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				if (albumIdIndex == NOT_FIXED) {
					albumIdIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
				}
				if (albumIdIndex == columnIndex) {
					int albumId = cursor.getInt(columnIndex);
					Drawable d = MusicAlbumManager.getInstance().getAlbumImage(MusicListActivity.this, albumId);
					ImageView iv = (ImageView)view;
					iv.setImageDrawable(d);
					return true;
				}
				return false;
			}
		});
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
				Cursor c = (Cursor)listView.getItemAtPosition(position);
				String title = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE));
				Intent result = new Intent();
				result.setData(uri);
				result.putExtra(PARAM_SELECT_AUDIO_TITLE, title);
				setResult(RESULT_OK, result);
				finish();
			}
		});
		getSupportLoaderManager().initLoader(0, null, this);

	}

	@Override
	public Loader<Cursor> onCreateLoader(int code, Bundle b) {
		return new CursorLoader(this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[] {MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.TITLE}, null, null, null);
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
