package org.tacademy.servicebind;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyTestService extends Service {

	boolean isRunning = true;
	int service_state = 0;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return myTestService;
	}
	
	IMyTestService.Stub myTestService = new IMyTestService.Stub() {
		
		@Override
		public boolean setState(String name, int state) throws RemoteException {
			// TODO Auto-generated method stub
			service_state = state;
			return true;
		}
		
		@Override
		public int getState(String name) throws RemoteException {
			// TODO Auto-generated method stub
			return service_state;
		}
	};

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(isRunning) {
					try {
						Thread.sleep(1000);
						service_state++;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//....
				}
				
			}
			
		});
		th.start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

}
