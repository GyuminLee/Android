package com.example.callerproject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;

import com.example.helloandroidtest.IHello;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
    	
    	super.onStart();
    	Intent i = new Intent();
    	ComponentName cn = new ComponentName("com.example.helloandroidtest","com.example.helloandroidtest.HelloService");
    	i.setComponent(cn);
    	bindService(i,conn,Context.BIND_AUTO_CREATE);
    }
    
    IHello mHello;
    
    ServiceConnection conn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mHello = null;
			
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
