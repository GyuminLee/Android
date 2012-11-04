package com.magnitude.app;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.magnitude.core.Plugin;

/**
 * The PluginDetails activity is used to show a user details about a plugin selected in the PluginList activity
 * @author Magnitude Client
 *
 */
public class PluginDetails extends Activity implements OnClickListener {

	/**
	 * The plugin from which we want to know details.
	 */
	private Plugin p;
	
	/**
	 * Progress Dialog for download.
	 */
	private ProgressDialog pd;
	/**
	 * Handler for job after the download is over.
	 */
	private Handler pdHandler;
	
	/**
	 * Outputstream where to put the apk file.
	 */
	private FileOutputStream fos;
	/**
	 * InputStream to read the apk file.
	 */
	private BufferedInputStream bis;
	/**
	 * Name of the apk file.
	 */
	private String fileName;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		
		//Find views we'll modify
		TextView pluginName = (TextView) findViewById(R.id.name);
		TextView pluginDesc = (TextView) findViewById(R.id.desc);
		TextView pluginCurrVersion = (TextView) findViewById(R.id.curr_version);
		TextView pluginLastVersion = (TextView) findViewById(R.id.last_version);
		Button install = (Button) findViewById(R.id.install);
		Button uninstall = (Button) findViewById(R.id.uninstall);
		Button cancel = (Button) findViewById(R.id.cancel);
		
		//Initialization of Handler
		pdHandler = new Handler() {
			public void handleMessage(Message msg) {
				// get the value from the message
				int progress = msg.arg1;
				pd.incrementProgressBy(progress);
			}
		};
		
		// Registering the class itself as listener on Buttons
		install.setOnClickListener(this);
		uninstall.setOnClickListener(this);
		cancel.setOnClickListener(this);

		//Getting the plugin Object from Intent
		p = this.getIntent().getParcelableExtra("plugin");
		
		//Updating values  of TextViews
		pluginName.setText(p.getName());
		pluginDesc.setText(p.getDescription());
		pluginCurrVersion.setText("Current version : "
				+ getPackageVersion(p.getIntent()));
		pluginLastVersion.setText("Last version : " + p.getVersion());

		//Setting Buttons statuses to enabled/disabled state according to Plugin state
		if (p.getStatus().equals(Plugin.STATUS_OUTDATED)) {
			install.setText("Upgrade");
		}
		if (p.getStatus().equals(Plugin.STATUS_INSTALLED)) {
			install.setEnabled(false);
		}
		if (p.getStatus().equals(Plugin.STATUS_AVAILABLE)) {
			uninstall.setEnabled(false);
		}
		if (p.getStatus().equals(Plugin.STATUS_NOT_RELEASED)) {
			install.setEnabled(false);
			uninstall.setEnabled(false);
		}

		
	}

	public void onClick(View v) {
		//If cancel Button was clicked
		if (v.getId() == R.id.cancel) {
			this.finish();
		}
		//If install Button was clicked
		if (v.getId() == R.id.install) {
			downloadAndInstallApkFile(p.getApkURL());
		}
		//If uninstall Button was clicked
		if (v.getId() == R.id.uninstall) {
			Uri packageURI = Uri.parse("package:" + p.getIntent());
			Intent uninstallIntent = new Intent(Intent.ACTION_DELETE,
					packageURI);
			startActivity(uninstallIntent);
			this.finish();
		}

	}

	/**
	 * Returns version of an installed package.
	 * @param action The package name.
	 * @return the package version. "not installed" if the package is not installed.
	 */
	public String getPackageVersion(String action) {
		PackageManager packageManager = this.getPackageManager();
		String res = "not installed";
		try {
			res = packageManager.getPackageInfo(action, 0).versionName;
		} catch (NameNotFoundException e) {
			return res;
		}
		return res;
	}

	/**
	 * Downloads an apk plugin and offers the user to install it on his system
	 * @param URL The URL of the apk file.
	 */
	public void downloadAndInstallApkFile(String URL) {
		URLConnection sourceUrl;
		pd = new ProgressDialog(this);
		pd.setCancelable(true);
		pd.setMessage("Downloading plugin package...");
		// set the progress to be horizontal
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// reset the bar to the default value of 0
		pd.setProgress(0);
		// display the progressbar
		pd.show();

		try {
			sourceUrl = new URL(URL).openConnection();
			Object data = sourceUrl.getContent();
			fileName = sourceUrl.toString();
			fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
			// create/open file in the 'data/data/<app namespace>/files'
			// directory
			fos = openFileOutput(fileName, Context.MODE_WORLD_READABLE);
			bis = new BufferedInputStream((InputStream) data);
			int totalProgress = sourceUrl.getContentLength();
			// set the maximum value
			pd.setMax(totalProgress);
			// Thread to inscrease progress bar while downloading
			new Thread(){
				int len1 = 0;
				int currentProgress = 0;
				byte[] buffer = new byte[1024];

				public void run() {
					try {
						while ((len1 = bis.read(buffer)) != -1) {
							currentProgress += len1;
							fos.write(buffer, 0, len1);
							pdHandler.sendMessage(pdHandler.obtainMessage(1, currentProgress,0));
							}
						fos.close();
						bis.close();
						// When download's over, close input and output streams and launch Install package activity
						Intent installIntent = new Intent(Intent.ACTION_VIEW);
						fileName=getFileStreamPath(fileName).getAbsolutePath();
						installIntent.setDataAndType(Uri.fromFile(new File(fileName)),
						"application/vnd.android.package-archive");
						startActivity(installIntent);
						finish();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}	
			}.start();	
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
