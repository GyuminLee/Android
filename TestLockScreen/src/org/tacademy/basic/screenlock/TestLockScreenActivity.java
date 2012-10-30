package org.tacademy.basic.screenlock;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class TestLockScreenActivity extends Activity {
    /** Called when the activity is first created. */
	private final static String TAG = "ScreenOnOffReceiver";

	private Handler mHandler = new Handler();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG,"call onCreate");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
//                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                );
        
        setContentView(R.layout.main);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),NotiService.class);
				startService(i);
			}
		});
        btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				KeyguardManager manager = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);  
				KeyguardLock lock = manager.newKeyguardLock(KEYGUARD_SERVICE);  
				lock.disableKeyguard();
				finish();
			}
		});
        
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            AlertDialog d = new AlertDialog.Builder(TestLockScreenActivity.this)
             .setTitle("tanchulai")
             .setMessage("bucuo de tanchulai")

             .create();

         d.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
         d.show();     
        }
     };
    
	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub
	    getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);  
	    super.onAttachedToWindow();
	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
    
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		finish();
		super.onStop();
	}

    
}