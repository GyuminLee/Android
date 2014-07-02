package com.example.sample4gallery;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Gallery;

public class MainActivity extends Activity {

	Gallery gallery;
	MyAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gallery = (Gallery) findViewById(R.id.gallery1);
		mAdapter = new MyAdapter(this);
		gallery.setAdapter(mAdapter);

		initData();

		if (mAdapter.getItemCount() != 0) {
			int size = mAdapter.getItemCount();
			int galleryPosition = ((Integer.MAX_VALUE / 2) / size) * size;
			gallery.setSelection(galleryPosition);
		}
	}

	private void initData() {
		mAdapter.add(R.drawable.gallery_photo_1);
		mAdapter.add(R.drawable.gallery_photo_2);
		mAdapter.add(R.drawable.gallery_photo_3);
		mAdapter.add(R.drawable.gallery_photo_4);
		mAdapter.add(R.drawable.gallery_photo_5);
		mAdapter.add(R.drawable.gallery_photo_6);
		mAdapter.add(R.drawable.gallery_photo_7);
		mAdapter.add(R.drawable.gallery_photo_8);
	}
}
