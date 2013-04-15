package com.example.hellonetwork;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class NaverBooksActivity extends Activity {

	EditText keywordView;
	ListView list;
	BookListAdapter mAdapter;
	Handler mHandler = new Handler();
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.naver_book_layout);
	    keywordView = (EditText)findViewById(R.id.keyword);
	    list = (ListView)findViewById(R.id.listView1);
	    Button btn = (Button)findViewById(R.id.search);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String keyword = keywordView.getText().toString();
				NaverBooksRequest request = new NaverBooksRequest(keyword);
				NetworkModel.getInstance().getNetworkData(request, new NetworkRequest.OnProcessCompletedListener() {
					
					@Override
					public void onCompleted(NetworkRequest request) {
						// TODO Auto-generated method stub
						NaverBooks books = (NaverBooks)request.getResult();
						mAdapter.addItem(books.items);
					}
				}, mHandler);
			}
		});
	    mAdapter = new BookListAdapter(this);
	    list.setAdapter(mAdapter);
	}

}
