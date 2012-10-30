package org.tacademy.basic.contentview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

public class DefaultKeyActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    setContentView(R.layout.default_key_mode);
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDefaultKeyMode(DEFAULT_KEYS_DISABLE);
				setTitle("KeyMode : disable");
		        new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(getWindow().getDecorView(), InputMethodManager.SHOW_FORCED);
					}
		        	
		        }, 500);
			}
		});
	    
	    btn = (Button)findViewById(R.id.button2);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);
				setTitle("KeyMode : shortcut");
			}
		});
	    
	    btn = (Button)findViewById(R.id.button3);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDefaultKeyMode(DEFAULT_KEYS_DIALER);
				setTitle("KeyMode : dialer");
			}
		});
	    
	    btn = (Button)findViewById(R.id.button4);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
				setTitle("KeyMode : search local");
			}
		});
	    
	    btn = (Button)findViewById(R.id.button5);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDefaultKeyMode(DEFAULT_KEYS_SEARCH_GLOBAL);
				setTitle("KeyMode : search global");
			}
		});
        new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(getWindow().getDecorView(), InputMethodManager.SHOW_FORCED);
			}
        	
        }, 500);
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.shortcuts, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "option item selected", Toast.LENGTH_SHORT).show();
		return super.onOptionsItemSelected(item);
	}
	
}
