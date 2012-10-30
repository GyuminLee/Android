package org.tacademy.basic.googleplaces;

import java.util.ArrayList;

import org.tacademy.basic.googleplaces.network.DownloadThread;
import org.tacademy.basic.googleplaces.network.NetworkRequest;
import org.tacademy.basic.googleplaces.placelist.GoogleItemAdapter;
import org.tacademy.basic.googleplaces.placelist.GooglePlaceConstants;
import org.tacademy.basic.googleplaces.placelist.GooglePlaceDetail;
import org.tacademy.basic.googleplaces.placelist.GooglePlaceItem;
import org.tacademy.basic.googleplaces.placelist.GooglePlaces;
import org.tacademy.basic.googleplaces.placelist.RequestPlaceDetail;
import org.tacademy.basic.googleplaces.placelist.RequestPlaceList;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class SampleGooglePlacesActivity extends Activity {
    /** Called when the activity is first created. */
	Spinner mSpinner;
	ListView mListView;
	EditText mKeyworkView;
	ArrayAdapter mSpinnerAdapter;
	GoogleItemAdapter mItemAdapter;
	LocationManager mLocationManager;
	Location mLocation;
	Handler mHandler = new Handler();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mSpinner = (Spinner)findViewById(R.id.type);
        mListView = (ListView)findViewById(R.id.listView1);
        mKeyworkView = (EditText)findViewById(R.id.keyword);
        ArrayList<String> data = new ArrayList<String>();
        data.add("None");
        for (int i = 0; i < GooglePlaceConstants.PLACE_TYPE_SEARCH_AND_ADD.length; i++) {
        	data.add(GooglePlaceConstants.PLACE_TYPE_SEARCH_AND_ADD[i]);
        }
        for (int i = 0; i < GooglePlaceConstants.PLACE_TYPE_SEARCH_ONLY.length; i++) {
        	data.add(GooglePlaceConstants.PLACE_TYPE_SEARCH_ONLY[i]);
        }
        
        mSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,data);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mSpinnerAdapter);
        
        mItemAdapter = new GoogleItemAdapter(this);
        mListView.setAdapter(mItemAdapter);
        
        Button btn = (Button)findViewById(R.id.search);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mLocation == null) {
					Toast.makeText(SampleGooglePlacesActivity.this, "Location not fixed", Toast.LENGTH_SHORT).show();
					return;
				}
				
				String keyword = mKeyworkView.getText().toString();
				String type = (String)mSpinner.getSelectedItem();
				if (keyword.equals("")) {
					keyword = null;
				}
				if (type.equals("None")) {
					type = null;
				}
				
				RequestPlaceList request = new RequestPlaceList(mLocation.getLatitude(),mLocation.getLongitude(), RequestPlaceList.DEFAULT_RADIUS, true, type, keyword);
				request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
					
					@Override
					public void onDownloadCompleted(int result, NetworkRequest request) {
						// TODO Auto-generated method stub
						if (result == NetworkRequest.PROCESS_SUCCESS) {
							GooglePlaces places = (GooglePlaces)request.getResult();
							mItemAdapter.clear();
							mItemAdapter.addAll(places.items);
						} else {
							Toast.makeText(SampleGooglePlacesActivity.this, "fail...", Toast.LENGTH_SHORT).show();
						}
					}
				});
				DownloadThread th = new DownloadThread(mHandler,request);
				th.start();
			}
		});
        
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				GooglePlaceItem item = (GooglePlaceItem)mItemAdapter.getItem(position);
				RequestPlaceDetail request = new RequestPlaceDetail(item, true);
				request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
					
					@Override
					public void onDownloadCompleted(int result, NetworkRequest request) {
						// TODO Auto-generated method stub
						if (result == NetworkRequest.PROCESS_SUCCESS) {
							GooglePlaceDetail detail = (GooglePlaceDetail)request.getResult();
							// show detail....
							Toast.makeText(SampleGooglePlacesActivity.this, "success detail...", Toast.LENGTH_SHORT).show();							
						} else {
							Toast.makeText(SampleGooglePlacesActivity.this, "fail...", Toast.LENGTH_SHORT).show();
						}
					}
				});
				DownloadThread th = new DownloadThread(mHandler,request);
				th.start();
			}
		});
        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 5, new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				mLocation = location;
			}

			@Override
			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub
				
			}
        	
        });
    }
}