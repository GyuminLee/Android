package org.tacademy.servicebind;

import java.util.Date;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class ServiceBindingActivity extends Activity {
	private IHelloService mService;
	private int mLastIndex;
	MyReceiver receiver;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    protected void onStart() {
    	super.onStart();
    	
    	Intent i = new Intent(this,HelloService.class);
    	boolean ret = bindService(i,
    			mConnection,
    			Context.BIND_AUTO_CREATE);
    	Log.i("BindActivity","bindService : "+ret);
    	
    	receiver = new MyReceiver();
    	IntentFilter filter = new IntentFilter("org.tacademy.servicebind.EVENT1");
    	receiver.setOnEventArrivedListener(new MyReceiver.OnEventArrivedListener() {
			
			@Override
			public void onEventArrived(Intent i) {
				// TODO Auto-generated method stub
				// ...
				int count = i.getIntExtra("count", 0);
			}
		});
    	registerReceiver(receiver,filter);
    }
    
    protected void onRestart() {
    	super.onRestart();
    	
    	putTimeStamp();
    }
    protected void onPause() {
    	super.onPause();
    	getTimeStamp();
    }
    
    protected void onStop() {
    	super.onStop();
    	unbindService(mConnection);
    	unregisterReceiver(receiver);
    }
    
    private void putTimeStamp() {
    	if (mService != null) {
    		try {
    			//mLastIndex = mService.addString((new Date()).toString());
    			mService.getCount(0);
    		} catch (RemoteException e) {
    			
    		}
    	}
    }
    private void getTimeStamp() {
    	if (mService != null) {
    		try {
    			//String s = mService.getString(mLastIndex);
    			//Log.i("Bind Activity","start time = " + s);
    			mService.setCount(0);
    		} catch (RemoteException e) {
    			
    		}
    	}
    }
    private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
    		Log.i("Bind Service",name.getShortClassName() + " connected.");
    		mService = IHelloService.Stub.asInterface(service);
    		//putTimeStamp();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			mService = null;
			Log.i("HelloBindServiceActivity",
					name.getShortClassName() + " disconnected");
			
		}
    };
}