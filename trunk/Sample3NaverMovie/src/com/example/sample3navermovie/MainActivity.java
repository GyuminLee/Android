package com.example.sample3navermovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
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

		EditText keywordView;
		ListView listView;
		MyAdapter mAdapter;
		String mKeyword;
		int totalCount;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			keywordView = (EditText)rootView.findViewById(R.id.editText1);
			listView = (ListView)rootView.findViewById(R.id.listView1);
			mAdapter = new MyAdapter(getActivity());
			listView.setAdapter(mAdapter);
			Button btn = (Button)rootView.findViewById(R.id.button1);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String keyword = keywordView.getText().toString();
					if (keyword != null && !keyword.equals("")) {
						NetworkModel.getInstance().getNaverMovie(keyword, 10, 1, new NetworkModel.OnNetworkResultListener<NaverMovies>() {

							@Override
							public void onResult(NaverMovies result) {
								mAdapter.addAll(result.item);
								totalCount = result.total;
							}
						});
						mKeyword = keyword;
					}
				}
			});
			listView.setOnScrollListener(new OnScrollListener() {
				
				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					
				}
				
				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					if (firstVisibleItem + visibleItemCount == totalItemCount) {
						if (mKeyword != null && !mKeyword.equals("") && totalCount > mAdapter.getCount() && !isLoading) {
							isLoading = true;
							int start = mAdapter.getCount() + 1;
							NetworkModel.getInstance().getNaverMovie(mKeyword, 10, start, new NetworkModel.OnNetworkResultListener<NaverMovies>() {

								@Override
								public void onResult(NaverMovies result) {
									mAdapter.addAll(result.item);
									isLoading = false;
								}
							});
						}
					}
				}
			});
			return rootView;
		}
		
		boolean isLoading = false;
	}

}
