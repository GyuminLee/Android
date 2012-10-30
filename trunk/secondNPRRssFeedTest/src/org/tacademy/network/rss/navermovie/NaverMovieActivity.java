package org.tacademy.network.rss.navermovie;

import java.util.ArrayList;

import org.tacademy.network.rss.ParentActivity;
import org.tacademy.network.rss.R;
import org.tacademy.network.rss.Nework.DownloadThread;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.util.DialogManager;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class NaverMovieActivity extends ParentActivity {

	EditText keyward;
	ListView list;
	NaverMovieAdapter mAdapter;
	ArrayList<NaverMovieItem> items = new ArrayList<NaverMovieItem>();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    
	    setContentView(R.layout.naver_movie);
	    keyward = (EditText)findViewById(R.id.keyward);
	    keyward.setText("»ç¶û");
	    list = (ListView)findViewById(R.id.movieList);
	    Button btn = (Button)findViewById(R.id.search);
	    mAdapter = new NaverMovieAdapter(this);
	    
	    list.setAdapter(mAdapter);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				mAdapter.removeAll();
				NaverMovieRequest request = new NaverMovieRequest(keyward.getText().toString());
				request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
					
					public void onDownloadCompleted(int result, NetworkRequest request) {
						dismissProgress();
						if (result == NetworkRequest.PROCESS_SUCCESS) {
							NaverMovies movies = (NaverMovies)request.getResult();
							mAdapter.addItem(movies.items);
						} else {
							Toast.makeText(NaverMovieActivity.this, "fail...", Toast.LENGTH_SHORT).show();
						}
						//NaverMovieAdapter adapter = new NaverMovieAdapter(getApplicationContext(),movies.items);
						//list.setAdapter(adapter);
					}
				});
				DownloadThread th = new DownloadThread(new Handler(),request);
				th.start();
				showProgress();
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(keyward.getWindowToken(), 0);
			}
		});
	    
	}	

}
