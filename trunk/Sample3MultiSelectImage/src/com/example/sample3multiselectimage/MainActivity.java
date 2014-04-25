package com.example.sample3multiselectimage;

import java.util.ArrayList;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements
			LoaderCallbacks<Cursor> {

		public PlaceholderFragment() {
		}

		GridView gridView;
		SimpleCursorAdapter mAdapter;

		int mIdColumnIndex = -1;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			gridView = (GridView) rootView.findViewById(R.id.gridView1);
			String[] from = { MediaStore.Images.Media._ID };
			int[] to = { R.id.imageContent };
			mAdapter = new SimpleCursorAdapter(getActivity(),
					R.layout.cursor_layout, null, from, to, 0);

			mAdapter.setViewBinder(new ViewBinder() {

				@Override
				public boolean setViewValue(View view, Cursor c, int columnIndex) {
					if (mIdColumnIndex == columnIndex) {
						ImageView imageView = (ImageView) view;
						long id = c.getLong(columnIndex);
						Bitmap bm = MediaStore.Images.Thumbnails.getThumbnail(
								getActivity().getContentResolver(), id,
								MediaStore.Images.Thumbnails.MINI_KIND, null);
						imageView.setImageBitmap(bm);
						return true;
					}
					return false;
				}
			});

			gridView.setAdapter(mAdapter);
			gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
			Button btn = (Button) rootView.findViewById(R.id.button1);
			btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					ArrayList<Uri> uris = new ArrayList<Uri>();
					SparseBooleanArray selectlist = gridView
							.getCheckedItemPositions();
					for (int i = 0; i < selectlist.size(); i++) {
						int position = selectlist.keyAt(i);
						if (selectlist.get(position)) {
							uris.add(ContentUris
									.withAppendedId(
											MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
											gridView.getItemIdAtPosition(position)));
						}
					}
				}
			});
			getLoaderManager().initLoader(0, null, this);
			return rootView;
		}

		@Override
		public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
			String[] projection = { MediaStore.Images.Media._ID };
			return new CursorLoader(getActivity(),
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
					null, null, null);
		}

		@Override
		public void onLoadFinished(Loader<Cursor> arg0, Cursor c) {
			mIdColumnIndex = c.getColumnIndex(MediaStore.Images.Media._ID);
			mAdapter.swapCursor(c);

		}

		@Override
		public void onLoaderReset(Loader<Cursor> arg0) {
			mAdapter.swapCursor(null);
		}
	}

}
