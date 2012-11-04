package com.magnitude.core;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.magnitude.app.PluginList;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * Utility class to Parse a json object representing pluginList
 * @author Magnitude client
 */
public class ParsePlugins {
	private JSONObject highestJSONOBject;
	private Context appContext;

	public ParsePlugins(JSONObject jo, Context appContext) {
		super();
		this.highestJSONOBject = jo;
		this.appContext = appContext;
	}

	public ArrayList<Plugin> parseObjects() {

		try {
			
			JSONArray array = new JSONArray(highestJSONOBject.getString("plugins"));

			int i = 0;
			
			PluginList.pluginArray.clear();
			while (i < array.length()) {
				JSONObject jo = array.getJSONObject(i);
				String name="Plugin Name";
				String id="Plugin id";
				String version="Plugin version";
				String description="Plugin desc";
				String intent="Plugin intent";
				String apkURL="pluginapkURL";
				String status="available";
				
				if (jo.has("name")) {
					name = jo.getString("name");
				}
				if (jo.has("id")) {
					id = jo.getString("id");
				}
				if (jo.has("version")) {
					version = jo.getString("version");
				}
				if (jo.has("description")) {
					description = jo.getString("description");
				}
				if (jo.has("intent")) {
					intent = jo.getString("intent");
				}
				if (jo.has("apk_URL")) {
					apkURL = jo.getString("apk_URL");
				}
				Plugin p = new Plugin(name,id,version,description,intent,apkURL,status);
				
				if (isIntentAvailable(p.getIntent())) {
					p.setStatus(Plugin.STATUS_INSTALLED);
					if (p.getVersion().compareTo(getPackageVersion(p.getIntent())) > 0) {
						p.setStatus(Plugin.STATUS_OUTDATED);
					}
				}
				else {
					p.setStatus(Plugin.STATUS_AVAILABLE);
				}
				if (jo.has("status")) {
					if (jo.get("status").equals("not released"))
						p.setStatus(Plugin.STATUS_NOT_RELEASED);
				}
				PluginList.pluginArray.add(p);
				i++;
			}
			

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return PluginList.pluginArray;
	}
	
	public boolean isIntentAvailable(String action) {
	    PackageManager packageManager = appContext.getPackageManager();
	    try {
			packageManager.getPackageInfo(action, 0);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}
	
	public String getPackageVersion(String action) {
		PackageManager packageManager = appContext.getPackageManager();
		String res="not found";
		try {
			res=packageManager.getPackageInfo(action, 0).versionName;
		} catch (NameNotFoundException e) {
			return res;
		}
		return res;
	}


}
