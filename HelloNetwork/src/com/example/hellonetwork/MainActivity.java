package com.example.hellonetwork;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements MyAdapter.OnAdapterImageClickListener {

	ListView mListView;
	MyAdapter mAdapter;
	Handler mHandler = new Handler();
	
	
	NetworkRequest.OnProcessCompletedListener mCompleteListener = new NetworkRequest.OnProcessCompletedListener() {
		
		@Override
		public void onCompleted(NetworkRequest request) {
			// TODO Auto-generated method stub
			NaverMovies movies = (NaverMovies)request.getResult();
			//ArrayList<ItemData> items = (ArrayList<ItemData>)request.getResult();
			mAdapter.addItems(movies.items);
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView)findViewById(R.id.listView1);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NaverMovieRequest request = new NaverMovieRequest("keyword");
				NetworkModel.getInstance().getNetworkData(request, 
						mCompleteListener, 
						mHandler);
			}
		});
        
        mAdapter = new MyAdapter(this);
        mAdapter.setOnAdapterImageClickListener(this);
        mListView.setAdapter(mAdapter);
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onImageClick(NaverMovieItem data) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "imageClicked", Toast.LENGTH_SHORT).show();
	}

    
}
