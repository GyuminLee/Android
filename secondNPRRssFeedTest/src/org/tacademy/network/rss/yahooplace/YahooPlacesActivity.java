package org.tacademy.network.rss.yahooplace;

import org.tacademy.network.rss.ParentActivity;
import org.tacademy.network.rss.R;
import org.tacademy.network.rss.Nework.DownloadThread;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.util.DialogManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class YahooPlacesActivity extends ParentActivity {

	EditText keywardView;
	ListView list;
	YahooPlaces places;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // TODO Auto-generated method stub
	    setContentView(R.layout.yahoo_places);
	    keywardView = (EditText)findViewById(R.id.keyward);
	    list = (ListView)findViewById(R.id.list);
	    Button btn = (Button)findViewById(R.id.search);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String keyward = keywardView.getText().toString();
				YahooPlaceRequest request = new YahooPlaceRequest(keyward);
				request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
					
					public void onDownloadCompleted(int result, NetworkRequest request) {
						dismissProgress();
						if (result == NetworkRequest.PROCESS_SUCCESS) {
							places = (YahooPlaces)request.getResult();
							// list에 place를 표시함.
							YahooPlacesAdapter adapter = new YahooPlacesAdapter(getApplicationContext(),places);
							adapter.setOnItemMapClickListener(new YahooPlacesAdapter.OnItemMapClickListener() {
								
								public void onItemMapClick(YahooPlacesItem item) {
									// Activity를 띄운다...
									Intent i = new Intent(getApplicationContext(),MyMapActivity.class);
									i.putExtra("item", item);
									startActivity(i);
								}
							});
							
							list.setAdapter(adapter);
						} else {
							Toast.makeText(YahooPlacesActivity.this, "fail...", Toast.LENGTH_SHORT).show();
						}
					}
				});
			    DownloadThread th = new DownloadThread(new Handler(),request);
			    th.start();
			    showProgress();
//			    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(request.urlString));
//			    startActivity(i);
				
			}
		});
	    
	    btn = (Button)findViewById(R.id.showMap);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if (places != null) {
					Intent intent = new Intent(getApplicationContext(),MyMapActivity.class);
					intent.putExtra("items", places);
					startActivity(intent);					
				}
			}
		});
	}

}
