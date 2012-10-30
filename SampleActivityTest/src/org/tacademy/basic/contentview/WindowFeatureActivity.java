package org.tacademy.basic.contentview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class WindowFeatureActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // TODO Auto-generated method stub
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.request_feature);
	    Button btn = (Button)findViewById(R.id.titleicon);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(WindowFeatureActivity.this,LeftRightIconActivity.class);
				startActivity(i);
			}
		});
	    
	    btn = (Button)findViewById(R.id.titleprogress);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(WindowFeatureActivity.this, TitleProgressActivity.class);
				startActivity(i);
			}
		});
	    	    
	    btn = (Button)findViewById(R.id.titlecustom);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(WindowFeatureActivity.this, CustomTitleActivity.class);
				startActivity(i);
				
			}
		});
	}

}
