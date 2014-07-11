package com.example.sample4imagelist;

import java.util.ArrayList;

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
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

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
		mAdapter = new SimpleCursorAdapter(this, R.layout.check_item, null, from, to, 0);
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
		gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
		
		getSupportLoaderManager().initLoader(0, null, this);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (gridView.getChoiceMode() == GridView.CHOICE_MODE_MULTIPLE) {
					SparseBooleanArray array = gridView.getCheckedItemPositions();
					ArrayList<String> imagePathList = new ArrayList<String>();
					for (int i = 0; i < array.size(); i++) {
						int position = array.keyAt(i);
						if (array.get(position)) {
							Cursor c = (Cursor)gridView.getItemAtPosition(position);
							String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
							imagePathList.add(path);
						}
					}
					
					Toast.makeText(MainActivity.this, "path : " + imagePathList.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA};
	
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
