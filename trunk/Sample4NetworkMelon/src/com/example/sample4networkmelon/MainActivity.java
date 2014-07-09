package com.example.sample4networkmelon;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sample4networkmelon.entity.Melon;
import com.example.sample4networkmelon.entity.NaverMovie;
import com.example.sample4networkmelon.entity.Song;
import com.example.sample4networkmelon.model.NetworkManager;
import com.example.sample4networkmelon.naver.NaverMovieAdapter;

public class MainActivity extends Activity {


	ListView listView;
	ArrayAdapter<Song> mAdapter;
	EditText keywordView;
	NaverMovieAdapter mMovieAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		keywordView = (EditText)findViewById(R.id.keyword_view);
		listView = (ListView)findViewById(R.id.listView1);
		Button btn = (Button)findViewById(R.id.btn_melon);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mAdapter = new ArrayAdapter<Song>(MainActivity.this, android.R.layout.simple_list_item_1, new ArrayList<Song>());
				listView.setAdapter(mAdapter);
				NetworkManager.getInstance().getMelonData(MainActivity.this, 10, 1 , new NetworkManager.OnResultListener<Melon>() {

					@Override
					public void onSuccess(Melon result) {
						for (Song s : result.songs.song) {
							mAdapter.add(s);
						}
						
					}

					@Override
					public void onFail(int code) {
						Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
					}
				});
//				new MelonTask().execute("http://apis.skplanetx.com/melon/charts/realtime?count=10&page=1&version=1");
			}
		});
		
		btn = (Button)findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mMovieAdapter = new NaverMovieAdapter(MainActivity.this);
				listView.setAdapter(mMovieAdapter);
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					NetworkManager.getInstance().getNaverMovieData(MainActivity.this, keyword, 10, 1, new NetworkManager.OnResultListener<NaverMovie>() {

						@Override
						public void onSuccess(NaverMovie result) {
							mMovieAdapter.addAll(result.item);
						}

						@Override
						public void onFail(int code) {
							Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		});
	}
	
//	class MelonTask extends AsyncTask<String, Integer, Melon> {
//		@Override
//		protected Melon doInBackground(String... params) {
//			String urlString = params[0];
//			try {
//				URL url = new URL(urlString);
//				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
////				conn.setRequestMethod("GET");
//				conn.setRequestProperty("Accept", "application/json");
//				conn.setRequestProperty("appKey", "458a10f5-c07e-34b5-b2bd-4a891e024c2a");
//				int responseCode = conn.getResponseCode();
//				if (responseCode == HttpURLConnection.HTTP_OK) {
//					InputStream is = conn.getInputStream();
//					Gson gson = new Gson();
//					MelonResult mr = gson.fromJson(new InputStreamReader(is), MelonResult.class);
//					return mr.melon;
//				}
//			} catch (MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//		
//		@Override
//		protected void onPostExecute(Melon result) {
//			super.onPostExecute(result);
//			if (result != null) {
//				for (Song s : result.songs.song) {
//					mAdapter.add(s);
//				}
//			}
//		}
//	}
}
