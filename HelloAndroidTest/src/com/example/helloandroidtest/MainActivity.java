package com.example.helloandroidtest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mHello != null) {
					try {
						mHello.power(10);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Intent i = new Intent(MainActivity.this,InfoActivity.class);
				Bundle options = ActivityOptionsCompat.makeCustomAnimation(MainActivity.this, R.anim.push_left_in, R.anim.push_left_out).toBundle();
				ActivityCompat.startActivity(MainActivity.this, i, options);
			}
		});
    }


    IHello mHello;
    
    @Override
    protected void onStart() {	
    	super.onStart();
    	Intent i = new Intent(this,HelloService.class);
    	bindService(i, conn, Context.BIND_AUTO_CREATE);
    }
    
    ServiceConnection conn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mHello = IHello.Stub.asInterface(service);
		}
	};
    
	@Override
	protected void onStop() {
		super.onStop();
		unbindService(conn);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
