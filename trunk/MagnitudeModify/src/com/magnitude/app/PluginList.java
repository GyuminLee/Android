package com.magnitude.app;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

import com.magnitude.core.ParsePlugins;
import com.magnitude.core.Plugin;
import com.magnitude.core.PluginAdapter;
import com.magnitude.tools.JSONHelper;

/**
 * The PluginList Activity enables user to manage plugins. It communicates with a server which give the available plugins
 * and checks for installed / outdated / available plugins.
 * @author Magnitude Client
 *
 */
public class PluginList extends ListActivity implements
		OnCreateContextMenuListener, OnItemClickListener {

	/**
	 * List of all plugins.
	 */
	public static ArrayList<Plugin> pluginArray = new ArrayList<Plugin>();
	/**
	 * Handler to show the list when it's done loading.
	 */
	final Handler uiThreadCallback = new Handler();

	/**
	 * ProgressDialog (wait for list to finish building).
	 */
	private ProgressDialog pd;
	/**
	 * PluginAdapter object to match Plugins with ListView elements.
	 */
	private PluginAdapter pa;
	/**
	 * The URL where to get json plugin list file.
	 */
	private String myURL;
	/**
	 * JSON Plugin list object Parser
	 */
	private ParsePlugins plugs;
	/**
	 * SharedPreferences object to store some small but important info
	 */
	private SharedPreferences sp;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Create the view and set login layout
		super.onCreate(savedInstanceState);
		
		//Initialize main window
		this.setContentView(R.layout.main);
		this.setTitle("Plugin list");
		
		//Initialize some fields
		sp = getSharedPreferences("settings", MODE_PRIVATE);
		
		myURL = sp.getString("server", "http://etud.insa-toulouse.fr/~benattar/android")
		+ "/plugins.json";
		
		pd = ProgressDialog.show(this, "Please Wait","Downloading Plugin list...");
		
		final Runnable runInUIThread = new Runnable() {
			public void run() {
				showList();
			}
		};

		new Thread() {
			public void run() {
				//Get JSON PLugin list object, then parse it to build actual Plugin List
				JSONObject jo = JSONHelper.getJSONObject(myURL);
				if (jo != null) {
				plugs = new ParsePlugins(jo, getApplicationContext());
				plugs.parseObjects();
				}
				//Once the List is built, do Post action and dismiss progress dialog.
				uiThreadCallback.post(runInUIThread);
				pd.dismiss();

			}
		}.start();

	}
	
	/**
	 * Shows Plugin list once its creation is done.
	 */
	public void showList() {
		//Get Layout inflater
		LayoutInflater vi = getLayoutInflater();
		setContentView(R.layout.main);
		//Set Adapter to match Array with items of the list
		pa = new PluginAdapter(this, R.layout.main, pluginArray, vi);
		setListAdapter(pa);
		//Get ListActivity 's Listview
		ListView lv = getListView();
		
		//Register listeners for click and long clicks on items
		lv.setOnItemClickListener(this);
		lv.setOnCreateContextMenuListener(this);

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Checks if a plugin was effectively installed, to update its status in listView
		if (pluginArray.size() > 0) {
			//methos isIntentAvailable uses PackageManager to check if a plugin is installed
			if (plugs.isIntentAvailable(pluginArray.get(requestCode)
					.getIntent())) {
				pluginArray.get(requestCode).setStatus(Plugin.STATUS_INSTALLED);

				String version = plugs.getPackageVersion(pluginArray.get(
						requestCode).getIntent());
				if (pluginArray.get(requestCode).getVersion()
						.compareTo(version) > 0) {
					pluginArray.get(requestCode).setStatus(
							Plugin.STATUS_OUTDATED);
				}
			} else {
				pluginArray.get(requestCode).setStatus(Plugin.STATUS_AVAILABLE);

			}
			//Useful to update the listView if data has changed
			pa.notifyDataSetChanged();
		}
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {	
		super.onCreateContextMenu(menu, v, menuInfo);	
		//find the plugin corresponding to the clicked item
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		int id = (int) info.id;
		Plugin p = pluginArray.get(id);
		//sets context menu title
		menu.setHeaderTitle(p.getName());
		
		//Build a different context menu according to Plugin status
		if (p.getStatus().equals(Plugin.STATUS_INSTALLED)) {
			menu.add(0, 1, 0, "Details");
			menu.add(0, 2, 0, "Uninstall");

		}
		if (p.getStatus().equals(Plugin.STATUS_AVAILABLE)) {
			menu.add(0, 1, 0, "Details");
			menu.add(0, 2, 0, "Install");

		}
		if (p.getStatus().equals(Plugin.STATUS_OUTDATED)) {
			menu.add(0, 1, 0, "Details");
			menu.add(0, 2, 0, "Update");
			menu.add(0, 3, 0, "Uninstall");

		}
		if (p.getStatus().equals(Plugin.STATUS_NOT_RELEASED)) {
			menu.add(0, 1, 0, "Details");

		}
	}

	public boolean onContextItemSelected(MenuItem item) {
		//find the plugin corresponding to the clicked item
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int pos = (int) info.id;
		Plugin p = pluginArray.get(pos);

			//Launch actions according to plugin status and selected option
			switch (item.getItemId()) {
			case 1:
				//DETAILS 
				if (p.getStatus().equals(Plugin.STATUS_AVAILABLE)
						|| p.getStatus().equals(Plugin.STATUS_INSTALLED)
						|| p.getStatus().equals((Plugin.STATUS_OUTDATED))) {
					Intent detailsIntent = new Intent(getApplicationContext(),
							PluginDetails.class);
					detailsIntent.putExtra("plugin", p);
					startActivityForResult(detailsIntent, pos);
				} else if (p.getStatus().equals(Plugin.STATUS_NOT_RELEASED)) {
					Intent detailsIntent = new Intent(getApplicationContext(),
							PluginDetails.class);
					detailsIntent.putExtra("plugin", p);
					startActivity(detailsIntent);
				}
				return true;
			case 2:
				// INSTALL
				if (p.getStatus().equals(Plugin.STATUS_AVAILABLE)
						|| p.getStatus().equals(Plugin.STATUS_OUTDATED)) {
					String fileName = downloadApkFile(p.getApkURL());
					Intent installIntent = new Intent(Intent.ACTION_VIEW);
					installIntent.setDataAndType(Uri
							.fromFile(new File(fileName)),
							"application/vnd.android.package-archive");
					startActivityForResult(installIntent, pos);
				} else if (p.getStatus().equals(Plugin.STATUS_INSTALLED)) {
					Uri packageURI = Uri.parse("package:" + p.getIntent());
					Intent uninstallIntent = new Intent(Intent.ACTION_DELETE,
							packageURI);
					startActivityForResult(uninstallIntent, pos);
				}
				return true;
			case 3:
				//UNINSTALL
				Uri packageURI = Uri.parse("package:" + p.getIntent());
				Intent uninstallIntent = new Intent(Intent.ACTION_DELETE,
						packageURI);
				startActivityForResult(uninstallIntent, pos);
				return true;
			default:
				return super.onContextItemSelected(item);
			}
	}
	
	/**
	 * Method which downloads an apk plugin File, in mode World Readable so it can be easily installed
	 * @param url url the apk file
	 * @return the downloaded file Path.
	 */
	public String downloadApkFile(String url) {
		//
		String fileName = null;
		URLConnection sourceUrl;
		try {
			//Connect to URL and gets content
			sourceUrl = new URL(url).openConnection();
			Object data = sourceUrl.getContent();
			
			//find Filename
			fileName = sourceUrl.toString();
			fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
			// create/open file in the 'data/data/<app namespace>/files'
			// directory
			FileOutputStream fos = openFileOutput(fileName,
					Context.MODE_WORLD_READABLE);
			byte[] buffer = new byte[1024];
			BufferedInputStream bis = new BufferedInputStream(
					(InputStream) data);
			int len1 = 0;
			while ((len1 = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len1);
			}
			fos.close();
			bis.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getFileStreamPath(fileName).getAbsolutePath();
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//find the plugin corresponding to the clicked item
		Plugin p = pluginArray.get(arg2);

		//SHOW DETAILS
		if (p.getStatus().equals(Plugin.STATUS_AVAILABLE)
				|| p.getStatus().equals(Plugin.STATUS_INSTALLED)
				|| p.getStatus().equals((Plugin.STATUS_OUTDATED))) {
			Intent detailsIntent = new Intent(getApplicationContext(),
					PluginDetails.class);
			detailsIntent.putExtra("plugin", p);
			startActivityForResult(detailsIntent, arg2);
		} else if (p.getStatus().equals(Plugin.STATUS_NOT_RELEASED)) {
			Intent detailsIntent = new Intent(getApplicationContext(),
					PluginDetails.class);
			detailsIntent.putExtra("plugin", p);
			startActivity(detailsIntent);
		}
	}

}
