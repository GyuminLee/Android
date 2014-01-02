package com.example.sample2simplebrowser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class BrowserActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.browser_layout);
	    Intent i = getIntent();
	    Uri uri = i.getData();
	    Toast.makeText(this, "uri : " + uri.toString(), Toast.LENGTH_SHORT).show();
	}

}
