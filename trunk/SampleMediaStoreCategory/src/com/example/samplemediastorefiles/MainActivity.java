package com.example.samplemediastorefiles;

import java.util.ArrayList;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		ArrayAdapter<String> mAdapter;
		ListView listView;
		String[] PROJECTION = { MediaStore.Images.ImageColumns.BUCKET_ID,
				"MAX(datetaken)",
				MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME };
		String BUCKET_GROUP_BY = "1) GROUP BY (1";
		String BUCKET_ORDER_BY = null; //"MAX(datetaken) DESC";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			listView = (ListView) rootView.findViewById(R.id.listView1);
			mAdapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1,
					new ArrayList<String>());
			listView.setAdapter(mAdapter);
			addData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//			addData(MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
			return rootView;
		}

		private void addData(Uri uri) {
			Cursor c = getActivity().getContentResolver().query(uri,
					PROJECTION, BUCKET_GROUP_BY, null, BUCKET_ORDER_BY);
			while (c.moveToNext()) {
				String bucketName = c
						.getString(c
								.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
				mAdapter.add(bucketName);
			}

		}
	}

}
