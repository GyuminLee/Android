package com.example.sample2gallerytest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Gallery;

public class MainActivity extends Activity {

	Gallery gallery;
	MyAdapter mAdapter;
	MyData[] array = new MyData[]{ new MyData(R.drawable.sample_0, "sample 0"), 
			new MyData(R.drawable.sample_1, "sample 1"),
			new MyData(R.drawable.sample_2, "sample 2"),
			new MyData(R.drawable.sample_3, "sample 3"),
			new MyData(R.drawable.sample_4, "sample 4"),
			new MyData(R.drawable.sample_5, "sample 5"),
			new MyData(R.drawable.sample_6, "sample 6"),
			new MyData(R.drawable.sample_7, "sample 7")
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gallery = (Gallery)findViewById(R.id.gallery1);
		mAdapter = new MyAdapter(this,array);
		gallery.setAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
