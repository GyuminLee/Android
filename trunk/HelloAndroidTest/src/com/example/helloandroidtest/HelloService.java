package com.example.helloandroidtest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class HelloService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return binder;
	}

	IHello.Stub binder = new IHello.Stub() {
		
		@Override
		public int power(int i) throws RemoteException {
			
			return i * i;
		}
	};
}
