package org.tacademy.basic.authentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AuthenticatorService extends Service {

	Authenticator mAuthenticator;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mAuthenticator = new Authenticator(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mAuthenticator.getIBinder();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// ¹»ÇØ¾ß ÇÏÁö....
	}

}
