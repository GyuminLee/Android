package com.example.sample4imagelist;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity implements LoaderCallbacks<Cursor>{

	GridView gridView;
	SimpleCursorAdapter mAdapter;
	public static final int NOT_INITIAL = -1;
	private int idColumnIndex = NOT_INITIAL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gridView = (GridView)findViewById(R.id.gridView1);
		String[] from = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME };
		int[] to = { R.id.imageView1, R.id.textView1 };
		mAdapter = new SimpleCursorAdapter(this, R.layout.item_view, null, from, to, 0);
		mAdapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor c, int columnIndex) {
				if (columnIndex == idColumnIndex) {
					ImageView imageView = (ImageView)view;
					long id = c.getLong(columnIndex);
//					BitmapFactory.Options options = new BitmapFactory.Options();
//					options.inSampleSize = 2;
					Bitmap bm = MediaStore.Images.Thumbnails.getThumbnail(getContentResolver(), id, MediaStore.Images.Thumbnails.MINI_KIND, null);
					imageView.setImageBitmap(bm);
					return true;
				}
				return false;
			}
		});
		gridView.setAdapter(mAdapter);
		
		getSupportLoaderManager().initLoader(0, null, this);
	}

	String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME};
	
	@Override
	public Loader<Cursor> onCreateLoader(int code, Bundle b) {	
		return new CursorLoader(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		idColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
		mAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
}
