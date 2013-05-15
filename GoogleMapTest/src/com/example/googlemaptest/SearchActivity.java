package com.example.googlemaptest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.googlemaptest.network.DownloadThread;
import com.example.googlemaptest.network.NetworkRequest;

public class SearchActivity extends Activity {

	ListView mListView;
	EditText keywordView;
	public static final String PARAM_FIELD_LOCATION = "currentLocation";
	public static final String RETURN_FIELD_POI = "searchpoi";
	
	Location mCurrentLocation;
	ArrayAdapter<GooglePlaceItem> mAdapter;
	Handler mHandler = new Handler();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    
	    setContentView(R.layout.search_activity);
	    keywordView = (EditText)findViewById(R.id.editText1);
	    mListView = (ListView)findViewById(R.id.listView1);
	    Button btn = (Button)findViewById(R.id.search);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String keyword = keywordView.getText().toString();
				if (mCurrentLocation == null || keyword == null || keyword.equals("")) {
					Toast.makeText(SearchActivity.this, "plz... enter location or keyword", Toast.LENGTH_SHORT).show();
					return;
				}
				
				GooglePlaceRequest request = new GooglePlaceRequest(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), keyword);
				request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
					
					@Override
					public void onDownloadCompleted(int result, NetworkRequest request) {
						// TODO Auto-generated method stub
						if (result == NetworkRequest.PROCESS_SUCCESS ) {
							GooglePlaces places = (GooglePlaces) request.getResult();
							if (places.results != null) {
								mAdapter = new ArrayAdapter<GooglePlaceItem>(SearchActivity.this,
										android.R.layout.simple_list_item_1, places.results);
								mListView.setAdapter(mAdapter);
							}
						}
					}
				});
				
				new DownloadThread(mHandler, request).start();
				
			}
		});
	    
	    
	    mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				GooglePlaceItem item = mAdapter.getItem(position);
				ArrayList<GooglePlaceItem> list = new ArrayList<GooglePlaceItem>();
				list.add(item);
				Intent i = new Intent();
				i.putExtra(RETURN_FIELD_POI, list);
				setResult(Activity.RESULT_OK, i);
				finish();
			}
		});
	    Intent intent = getIntent();
	    
	    mCurrentLocation = intent.getParcelableExtra(PARAM_FIELD_LOCATION);	    
	    if (mCurrentLocation == null) {
	    	LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    	lm.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
				
				@Override
				public void onStatusChanged(String provider, int status, Bundle extras) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProviderEnabled(String provider) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProviderDisabled(String provider) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onLocationChanged(Location location) {
					// TODO Auto-generated method stub
					mCurrentLocation = location;
				}
			}, null);
	    }
	}

}
