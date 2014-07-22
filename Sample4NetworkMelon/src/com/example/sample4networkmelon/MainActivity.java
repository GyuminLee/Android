package com.example.sample4networkmelon;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sample4networkmelon.browser.BrowserActivity;
import com.example.sample4networkmelon.entity.Melon;
import com.example.sample4networkmelon.entity.MovieItem;
import com.example.sample4networkmelon.entity.NaverMovie;
import com.example.sample4networkmelon.entity.Song;
import com.example.sample4networkmelon.model.NetworkManager;
import com.example.sample4networkmelon.naver.NaverMovieAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class MainActivity extends Activity {

	ListView listView;
	ArrayAdapter<Song> mAdapter;
	EditText keywordView;
	NaverMovieAdapter mMovieAdapter;
	PullToRefreshListView pullView;
	String mKeyword = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("NaverMovie");
		keywordView = (EditText) findViewById(R.id.keyword_view);
		pullView = (PullToRefreshListView)findViewById(R.id.listView1);
		pullView.setOnPullEventListener(new OnPullEventListener<ListView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView,
					State state, Mode direction) {
				if (mKeyword != null && mMovieAdapter != null) {
					int start = mMovieAdapter.getStart();
					if (start != NaverMovieAdapter.NO_MORE) {
						NetworkManager.getInstance().getNaverMovieData(MainActivity.this, mKeyword, 10, start,new NetworkManager.OnResultListener<NaverMovie>() {

							@Override
							public void onSuccess(NaverMovie result) {
								mMovieAdapter.addAll(result.item);
								mMovieAdapter.setTotal(result.total);
								pullView.onRefreshComplete();
							}

							@Override
							public void onFail(int code) {
								Toast.makeText(MainActivity.this, "fail",
										Toast.LENGTH_SHORT).show();
								pullView.onRefreshComplete();
							}
						} );
					} else {
						Toast.makeText(MainActivity.this, "no more", Toast.LENGTH_SHORT).show();
						pullView.onRefreshComplete();
					}
				}
				
			}
		});
		listView = pullView.getRefreshableView();
		Button btn = (Button) findViewById(R.id.btn_melon);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mAdapter = new ArrayAdapter<Song>(MainActivity.this,
						android.R.layout.simple_list_item_1,
						new ArrayList<Song>());
				listView.setAdapter(mAdapter);
				listView.setOnItemClickListener(null);
				NetworkManager.getInstance().getMelonData(MainActivity.this,
						10, 1, new NetworkManager.OnResultListener<Melon>() {

							@Override
							public void onSuccess(Melon result) {
								for (Song s : result.songs.song) {
									mAdapter.add(s);
								}

							}

							@Override
							public void onFail(int code) {
								Toast.makeText(MainActivity.this, "Fail",
										Toast.LENGTH_SHORT).show();
							}
						});
				// new
				// MelonTask().execute("http://apis.skplanetx.com/melon/charts/realtime?count=10&page=1&version=1");
			}
		});

		btn = (Button) findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mMovieAdapter = new NaverMovieAdapter(MainActivity.this);
				listView.setAdapter(mMovieAdapter);
				listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						MovieItem item = (MovieItem) listView
								.getItemAtPosition(position);
						if (item.link != null && !item.link.equals("")) {
							Intent i = new Intent(MainActivity.this,
									BrowserActivity.class);
							i.setData(Uri.parse(item.link));
							startActivity(i);
						}
					}
				});
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					int start = mMovieAdapter.getStart();
					if (start != NaverMovieAdapter.NO_MORE) {
						mKeyword = keyword;
						NetworkManager.getInstance().getNaverMovieData(
							MainActivity.this, keyword, 10, start,
							new NetworkManager.OnResultListener<NaverMovie>() {

								@Override
								public void onSuccess(NaverMovie result) {
									mMovieAdapter.addAll(result.item);
									mMovieAdapter.setTotal(result.total);
								}

								@Override
								public void onFail(int code) {
									Toast.makeText(MainActivity.this, "fail",
											Toast.LENGTH_SHORT).show();
								}
							});
					}
				}
			}
		});
	}

	// class MelonTask extends AsyncTask<String, Integer, Melon> {
	// @Override
	// protected Melon doInBackground(String... params) {
	// String urlString = params[0];
	// try {
	// URL url = new URL(urlString);
	// HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	// // conn.setRequestMethod("GET");
	// conn.setRequestProperty("Accept", "application/json");
	// conn.setRequestProperty("appKey",
	// "458a10f5-c07e-34b5-b2bd-4a891e024c2a");
	// int responseCode = conn.getResponseCode();
	// if (responseCode == HttpURLConnection.HTTP_OK) {
	// InputStream is = conn.getInputStream();
	// Gson gson = new Gson();
	// MelonResult mr = gson.fromJson(new InputStreamReader(is),
	// MelonResult.class);
	// return mr.melon;
	// }
	// } catch (MalformedURLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(Melon result) {
	// super.onPostExecute(result);
	// if (result != null) {
	// for (Song s : result.songs.song) {
	// mAdapter.add(s);
	// }
	// }
	// }
	// }
}
