package com.magnitude.app;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.magnitude.ARKitapi.ARKitCameraView;
import com.magnitude.ARKitapi.ARLayout;
import com.magnitude.ARKitapi.ARSphericalView;
import com.magnitude.app.googleplaces.GooglePlaceItem;
import com.magnitude.app.googleplaces.GooglePlaces;
import com.magnitude.app.googleplaces.RequestPlaceList;
import com.magnitude.app.network.DownloadThread;
import com.magnitude.app.network.NetworkRequest;
import com.magnitude.libs.MagnitudePOI;
import com.magnitude.libs.PoiAttributes;

/**
 * MagnitudeActivity is the main class of Magnitude client application. It should be
 * extended but source code is accessible as Magnitude. <br>
 * is completely open-source. Some of the work of Chris Haseman has been used
 * and improved in order to build this classes.
 * 
 * @author Magnitude client
 * 
 */
public class MagnitudeActivity extends Activity implements LocationListener,
		OnMultiChoiceClickListener, OnClickListener {

	/**
	 * Application context.
	 */
	public static volatile Context ctx;
	/**
	 * A custom camera view we use to show camrecorder data.
	 */
	private ARKitCameraView cv;
	/**
	 * ARKit ARLayout (Makes AR calculus and drawings).
	 */
	protected ARLayout ar;
	/**
	 * Base Layout on which we'll add our "layers".
	 */
	private FrameLayout fl;

	/**
	 * HashMap of Plugins names with corresponding intents
	 */
	private HashMap<String, String> installedPlugins;
	/**
	 * List of enabled Plugins.
	 */
	private ArrayList<String> enabledPlugins;
	/**
	 * An Array with all the POI's info
	 */
	private ArrayList<PoiAttributes> poiArray = new ArrayList<PoiAttributes>();
	private Receiver receiver;
	private LocationManager locMan;
	public static final String EVENT_POI = "ServicePOI";
	public static final String POI_ARRAY = "poiArray";
	public final String[] ARRAY_TEMPLATE = new String[0];
	private static int SERVICE_TYPE_TOUCHEABLE = 0;
	private static int SERVICE_TYPE_GETTER = 1;
	private MagnitudeActivity me;
	private ProgressDialog pd;
	private Intent intentLaunching;
	private Timer t;
	private Handler mHandler = new Handler();
	
	/**
	 * What the API should do when a change of Location is detected <br>
	 * the doTreatment method will be implemented by subclasses.
	 */
	public void onLocationChanged(Location location) {
		ar.setCurLocation(location);
		LocationManager locMan = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
		ar.getGpsIcon().update(ar.getCurLocation().getAccuracy(),
				location.getAccuracy());
		locMan.removeUpdates(this);
	}

	public void onStart() {
		super.onStart();
		getAr().startGPS();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initWindowParameters();
		initComponents();
		initLayers();
		initLocationManagement();
		initReceiver();
		}

	public void onStop() {
		cv.closeCamera();
		if (getAr() != null)
			getAr().stopGPS();
		locMan.removeUpdates(this);
		super.onStop();
	}

	public void onDestroy() {
		cv.closeCamera();
		if (getAr() != null)
			getAr().close();
		locMan.removeUpdates(this);
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	/**
	 * A Receiver to respond to Services broadcast events
	 * 
	 * @author Jonathan
	 * 
	 */
	public class Receiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			String plugName = intent.getAction().replace(".BACK_EVENT","");
			if (bundle != null) {
				pd.dismiss();
				t.cancel();
				Toast.makeText(ctx,"Plugin "+plugName.replace("com.magnitude.plugin.","")+" is ready",Toast.LENGTH_LONG).show();
				enabledPlugins.add(plugName.replace("com.magnitude.plugin.",""));
				if (bundle.getInt("type") == SERVICE_TYPE_TOUCHEABLE) {
					getAr().addToucheableAction(plugName);
				} else if (bundle.getInt("type") == SERVICE_TYPE_GETTER) {
					poiArray = bundle.getParcelableArrayList(POI_ARRAY);
					for (int i = 0; i < poiArray.size(); i++) {
						MagnitudePOI poi = new MagnitudePOI(MagnitudeActivity.ctx,
								me, poiArray.get(i));
						poi.setPlugin(plugName);
						getAr().addARView(poi);
						getAr().updateLayouts(0, 0, new Location("test"));
					}
				}
			}
		}
	}

	/**
	 * Initializes the Receiver
	 */
	public void initReceiver() {
		receiver = new Receiver();

		refreshInstalledPlugins();
		String[] plugs = installedPlugins.keySet().toArray(ARRAY_TEMPLATE);
		IntentFilter filter = new IntentFilter();
		for (String s : plugs) {
			filter.addAction(installedPlugins.get(s) + ".BACK_EVENT");
		}
		registerReceiver(receiver, filter);

	}

	/**
	 * Initializes Window parameters
	 */
	public void initWindowParameters() {
		// As we're in a AR App, we want to be in landscape mode, with the max
		// surface for camrecorder image (so no titlebar).
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * Initializes Main fields
	 */
	public void initComponents() {
		ctx = this.getApplication().getApplicationContext();
		setAr(new ARLayout(ctx, this));
		cv = new ARKitCameraView(ctx);
		fl = new FrameLayout(ctx);
		installedPlugins = new HashMap<String, String>();
		enabledPlugins = new ArrayList<String>();
		me = this;
	}

	/**
	 * Initializes Layers superposition
	 */
	public void initLayers() {
		int width = (int) getAr().getScreenWidth();
		int height = (int) getAr().getScreenHeight();
		fl.addView(cv, width, height);
		fl.addView(getAr(), width, height);
		setContentView(fl);
	}

	/**
	 * Initializes GPS
	 */
	public void initLocationManagement() {
		locMan = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
		locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1,
				this);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	/**
	 * Build the list of installed Plugins
	 */
	public void refreshInstalledPlugins() {
		for (PackageInfo p : getPackageManager().getInstalledPackages(0)) {
			if (p.packageName.contains("com.magnitude.plugin")) {
				installedPlugins.put(p.packageName.replace(
						"com.magnitude.plugin.", ""), p.packageName);
			}
		}
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		// If we want to enable plugin. Show plugin list
		// TODO Clean this.
		if (item.getItemId() == R.id.enable) {
			refreshInstalledPlugins();
			boolean[] checks = new boolean[installedPlugins.size()];
			String[] plugs = installedPlugins.keySet().toArray(ARRAY_TEMPLATE);
			int i = 0;
			for (String s : plugs) {
				if (enabledPlugins.contains(s)) {
					checks[i] = true;
				} else {
					checks[i] = false;
				}
				i++;
			}
			final AlertDialog.Builder ab = new Builder(this);
			ab.setTitle("Activate Plugin");
			ab.setPositiveButton("Ok", this);
			ab.setMultiChoiceItems(plugs, checks, this);
			ab.create();
			ab.show();
		} else if (item.getItemId() == R.id.plugins) {
			Intent ListeIntent = new Intent(this, PluginList.class);
			startActivity(ListeIntent);
		} else if (item.getItemId() == R.id.settings) {
			Intent settingsIntent = new Intent(this, SettingsActivity.class);
			startActivityForResult(settingsIntent, 21);
		} else if (item.getItemId() == R.id.poi) {
			Intent poiAddIntent = new Intent(this, PoiAddActivity.class);
			startActivityForResult(poiAddIntent, 42);
		} else if (item.getItemId() == R.id.google) {
			Location loc = ar.getCurLocation();
			if (loc == null) {
				loc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}
			if (loc != null) {
				RequestPlaceList request = new RequestPlaceList(loc.getLatitude(), loc.getLongitude());
				request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
					
					@Override
					public void onDownloadCompleted(int result, NetworkRequest request) {
						// TODO Auto-generated method stub
						if (result == NetworkRequest.PROCESS_SUCCESS) {
							GooglePlaces places = (GooglePlaces)request.getResult();
							String pluginName = "com.magnitude.plugin.whereIsMy";
							for (GooglePlaceItem item : places.items) {
								PoiAttributes poiattr = new PoiAttributes(item.name,"com.magnitude.poi",
										item.geometry.location.lat,item.geometry.location.lng,0,0);
								MagnitudePOI poi = new MagnitudePOI(MagnitudeActivity.ctx,
										null, poiattr);
								poi.setPlugin(pluginName);
								getAr().addARView(poi);
							}
							showMessage("google places added");
						} else {
							showMessage("network error!!!");
						}
					}
				});
				DownloadThread th = new DownloadThread(mHandler, request);
				th.start();
			} else {
				showMessage("Location not fixed");
			}
		}
		return true;
	}

	private void showMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 21) {
			getAr().updateSettings();
		} else if(requestCode == 42 && data != null) {
			Bundle bundle = data.getExtras();
			String plugName = data.getAction().replace(".END_ACTIVITY","");
			if (bundle != null) {
					poiArray = bundle.getParcelableArrayList(POI_ARRAY);
					for (int i = 0; i < poiArray.size(); i++) {
						MagnitudePOI poi = new MagnitudePOI(MagnitudeActivity.ctx,
								null, poiArray.get(i));
						poi.setPlugin(plugName);
						getAr().addARView(poi);
				}
			}
		}
			
	}

	/**
	 * Empty method. This is normal. This method should be Overrided in actual
	 * applications
	 */
	public void doTreatment() {
	}

	/**
	 * ArLayout setter
	 * 
	 * @param ar
	 *            the ARLayout to set
	 */
	public void setAr(ARLayout ar) {
		this.ar = ar;
	}

	/**
	 * ARLayout getter
	 * 
	 * @return ARKitActivity's ARLAyout
	 */
	public ARLayout getAr() {
		return ar;
	}

	/**
	 * Adds a poi to ARLayout
	 * 
	 * @param poi
	 */
	public void addToARView(ARSphericalView poi) {
		getAr().addARView(poi);
	}

	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		final String[] ARRAY_TEMPLATE = new String[0];
		String plugin = installedPlugins.keySet().toArray(ARRAY_TEMPLATE)[which];
		Intent bindIntent = new Intent(installedPlugins.get(plugin)+ ".START_EVENT");
		if (isChecked) {
			startService(bindIntent);
			intentLaunching = bindIntent;
			pd = ProgressDialog.show(this, "Please Wait","Launching "+plugin+" ...");
			PluginThread checkPlugin = new PluginThread();
			t = new Timer();
			t.schedule(checkPlugin,15000);
			dialog.dismiss();
		} else {
			stopService(bindIntent);
			getAr().removeToucheableAction(bindIntent.getAction().replace(".START_EVENT",""));
			enabledPlugins.remove(plugin);
			Enumeration<ARSphericalView> e = getAr().getArViews().elements();
			while (e.hasMoreElements()) {
				MagnitudePOI cast = (MagnitudePOI) e.nextElement();
				if (cast.getPlugin() != null) {
					if (cast.getPlugin().equals(installedPlugins.get(plugin))) {
						getAr().getArViews().remove(cast);
						e = getAr().getArViews().elements();
					}
				}
			}
			Toast.makeText(this, plugin + " disabled", Toast.LENGTH_SHORT)
					.show();
		}
	}
	
	private class PluginThread extends TimerTask {

		public PluginThread(){
			
		}
		
		@Override
		public void run() {
			stopService(intentLaunching);
			pd.dismiss();
			Toast.makeText(me,"Error while launching the plugin, please try again.",Toast.LENGTH_LONG).show();
		}
		
	}

	public void onClick(DialogInterface dialog, int which) {
		dialog.dismiss();
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
