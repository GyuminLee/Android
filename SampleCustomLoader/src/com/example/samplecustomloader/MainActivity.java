package com.example.samplecustomloader;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<List<ItemData>> {

	ListView listView;
	MyAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new MyAdapter(this);
		listView.setAdapter(mAdapter);
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public Loader<List<ItemData>> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return new AppLoader(this);
	}

	@Override
	public void onLoadFinished(Loader<List<ItemData>> arg0, List<ItemData> arg1) {
		// TODO Auto-generated method stub
		mAdapter.set(arg1);
	}

	@Override
	public void onLoaderReset(Loader<List<ItemData>> arg0) {
		// TODO Auto-generated method stub
		mAdapter.set(null);
	}

}
