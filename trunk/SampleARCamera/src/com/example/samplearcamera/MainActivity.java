package com.example.samplearcamera;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Menu;
import android.view.MenuItem;

import com.example.samplearcamera.googleplaces.GooglePlaceItem;
import com.example.samplearcamera.library.ARLocationManager;
import com.example.samplearcamera.library.ARSensorManager;
import com.example.samplearcamera.library.CameraView;
import com.example.samplearcamera.library.OverlayView;
import com.example.samplearcamera.library.POIManager;
import com.example.samplearcamera.map.ARMap;

public class MainActivity extends Activity {

	CameraView mCameraView;
	OverlayView mOverlayView;
	Handler mHandler = new Handler();
	WakeLock lock;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCameraView = (CameraView)findViewById(R.id.cameraview);
        mOverlayView = (OverlayView)findViewById(R.id.overlay);
        ARLocationManager.getInstance().start(mMakerUpdateListener);
        ARSensorManager.getInstance().startSensing();
        //mCameraView.setViewState(ViewState.VIEW_STATE_PREVIEW);
        mOverlayView.startUpdateScreen();
        lockScreen();
    }
    
    private void lockScreen() {
    	PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
    	lock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "ARCamera");
    	lock.acquire();
    }
    
    private void unlockScreen() {
    	lock.release();
    }
    
    ARLocationManager.OnMarkerUpdateListener mMakerUpdateListener = new ARLocationManager.OnMarkerUpdateListener() {
		
		@Override
		public void onMarkerUpdate(Location location) {
			getPOI(location);
		}
	};
	
	public void getPOI(Location location) {
		POIManager.getInstance().getNewGooglePlaceItems(location, mPoiUpdateListener , mHandler);		
	}
	
	POIManager.OnUpdatedListener mPoiUpdateListener = new POIManager.OnUpdatedListener() {
		
		@Override
		public void onUpdated(ArrayList<GooglePlaceItem> items) {
			// TODO Auto-generated method stub
			mOverlayView.clear();
			for (GooglePlaceItem item : items) {
				mOverlayView.addMarker(item.id, item.geometry.location.lat, item.geometry.location.lng);
			}
		}
	};
	
    @Override
    protected void onDestroy() {
    	mCameraView.releaseCamera();
    	ARLocationManager.getInstance().stop(mMakerUpdateListener);
    	ARSensorManager.getInstance().stopSensing();
    	unlockScreen();
    	super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	if (item.getItemId() == R.id.item_map) {
    		Intent i = new Intent(this,ARMap.class);
    		startActivity(i);
    		return true;
    	} 
    	return super.onOptionsItemSelected(item);
    }
}
