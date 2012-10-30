package org.tacademy.basic.messenger;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class MessengerService extends Service {

	public static final String MESSENGER_FIELD = "messenger";
	public static final int COMMAND_SEND_MESSAGE = 1;
	
	private Messenger serviceMessenger;
	
	private ArrayList<Messenger> messengerArray = new ArrayList<Messenger>();
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Messenger messenger = intent.getParcelableExtra(MESSENGER_FIELD);
		messengerArray.add(messenger);
		
		return serviceMessenger.getBinder();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		serviceMessenger = new Messenger(serviceHandler);
	}

	private Handler serviceHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what) {
				case COMMAND_SEND_MESSAGE :
					String text = (String)msg.obj;
					for (int i = messengerArray.size() - 1; i >= 0; i--) {
						try {
							Messenger msgr = messengerArray.get(i);
							msgr.send(Message.obtain(null, COMMAND_SEND_MESSAGE, text));
						} catch (RemoteException e) {
							e.printStackTrace();
							messengerArray.remove(i);
						}
					}
					break;
				default :
					break;
			}
		}
	};
}
