package org.tacademy.basic.messenger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SampleMessengerActivity extends Activity {
    /** Called when the activity is first created. */
	Messenger mServiceMessenger = null;
	Messenger myMessenger;

	Handler mHandler = new Handler();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myMessenger = new Messenger(myHandler);
        Intent i = new Intent(this,MessengerService.class);
        i.putExtra(MessengerService.MESSENGER_FIELD, myMessenger);
        
        boolean b = bindService(i, conn, Context.BIND_AUTO_CREATE);
        
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mServiceMessenger != null) {
					try {
						mServiceMessenger.send(Message.obtain(null, MessengerService.COMMAND_SEND_MESSAGE, "hello"));
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
    }
    
    ServiceConnection conn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			mServiceMessenger = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mServiceMessenger = new Messenger(service);
			
		}
	};
    
    Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what) {
				case MessengerService.COMMAND_SEND_MESSAGE :
					final String message = (String)msg.obj;
					mHandler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(SampleMessengerActivity.this, "message : " + message, Toast.LENGTH_SHORT).show();
						}
						
					});
					break;
				default :
					break;
			}
		}
    	
    };

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unbindService(conn);
		super.onDestroy();
	}
    
    
}