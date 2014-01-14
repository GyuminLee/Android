package com.example.sample2imagelist;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends FragmentActivity implements
		LoaderCallbacks<Cursor> {

	ListView listView;
	SimpleCursorAdapter mAdapter;
	ImageView centerView;
	String[] columns = { MediaStore.Images.Media._ID,
			MediaStore.Images.Media.DISPLAY_NAME };

	private final static int NOT_FIXED = -1;
	private int idIndex = NOT_FIXED;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		centerView = (ImageView) findViewById(R.id.imageView1);
		centerView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				centerView.setVisibility(View.GONE);
			}
		});
		mAdapter = new SimpleCursorAdapter(this, R.layout.item_layout2, null,
				columns, new int[] { R.id.iconView, R.id.titleView }, 0);
		mAdapter.setViewBinder(new ViewBinder() {

			@Override
			public boolean setViewValue(View view, Cursor c, int columnIndex) {
				if (idIndex == NOT_FIXED) {
					idIndex = c.getColumnIndex(MediaStore.Images.Media._ID);
				}
				if (idIndex == columnIndex) {
					long id = c.getLong(columnIndex);
					ImageView iv = (ImageView) view;
					Bitmap bm = MediaStore.Images.Thumbnails.getThumbnail(
							getContentResolver(), id,
							MediaStore.Images.Thumbnails.MINI_KIND, null);
					iv.setImageBitmap(bm);
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
				Uri uri = ContentUris.withAppendedId(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
				centerView.setImageURI(uri);
				centerView.setVisibility(View.VISIBLE);
				Cursor c = (Cursor)listView.getItemAtPosition(position);
				
			}
		});
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	String[] projection = { MediaStore.Images.Media._ID,
			MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA,
			MediaStore.Images.Media.LATITUDE, MediaStore.Images.Media.LONGITUDE };

	@Override
	public Loader<Cursor> onCreateLoader(int code, Bundle b) {
		return new CursorLoader(this,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
				null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursor, Cursor c) {
		mAdapter.swapCursor(c);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursor) {
		mAdapter.swapCursor(null);
	}

}
