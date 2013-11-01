package com.example.sampleviewflipper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.TextSwitcher;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {

	ViewFlipper viewFlipper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper1);
		Button btn = (Button)findViewById(R.id.prev);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				viewFlipper.showPrevious();
			}
		});
		
		btn = (Button)findViewById(R.id.next);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				viewFlipper.showNext();
			}
		});
		TextSwitcher ts = (TextSwitcher)findViewById(R.id.textSwitcher1);
		ts.setText("....");
		ImageSwitcher is = (ImageSwitcher)findViewById(R.id.imageSwitcher1);
		is.setImageResource(R.drawable.ic_launcher);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
