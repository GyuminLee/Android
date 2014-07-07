package com.example.sample4configuration;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Configuration config = getResources().getConfiguration();
		if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
			Toast.makeText(this, "onCreate portait", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "onCreate landscape", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void onConfigurationChanged(Configuration config) {		
		super.onConfigurationChanged(config);
		if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
			Toast.makeText(this, "onConfigurationChanged portait", Toast.LENGTH_SHORT).show();
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		} else {
			Toast.makeText(this, "onConfigurationChanged landscape", Toast.LENGTH_SHORT).show();
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		
	}
}
