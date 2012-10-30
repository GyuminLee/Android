package org.tacademy.network.rss.chat;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.tacademy.network.rss.util.LocalBinder;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class ChatService extends Service {

	public static final int XMPP_LOGIN_OK = 1;
	public static final int XMPP_LOGIN_FAIL = 2;
	private static final String DOMAIN = "talk.google.com";
	
	public static final int XMPP_ACTION_LOGIN = 1;
	public static final int XMPP_ACTION_LOGOUT = 2;
	
	XMPPConnection mXmppConnection;
	String mAuthToken;
	String mAccountName;
	private boolean isLogin = false;
	private boolean isRetry = false;
	int mAction;
	
	public interface OnLoginResultListener {
		public void onLoginResult(int result,int action);
	}
	
	OnLoginResultListener mListener;
	
	public void setOnLoginResultListener(OnLoginResultListener listener) {
		mListener = listener;
	}
	
	public interface OnReceiveMessageListener {
		public void onReceiveMessage(String email,String message);
	}
	
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return new LocalBinder<ChatService>(this);
	}
	
	

	public void startLogin(String accountName,String authToken) {
		mAccountName = accountName;
		mAuthToken = authToken;
		mAction = XMPP_ACTION_LOGIN;
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				login();
			}
			
		});
		th.start();
	}

	private void login() {
		ConnectionConfiguration config = new ConnectionConfiguration(DOMAIN,5222,"gmail.com");

		mXmppConnection = new XMPPConnection(config);
		try {
	        SASLAuthentication.registerSASLMechanism( "X-GOOGLE-TOKEN", GoogleTalkAuthentication.class );
	        SASLAuthentication.supportSASLMechanism( "X-GOOGLE-TOKEN", 0 );
			mXmppConnection.connect();
			mXmppConnection.login(mAccountName, mAuthToken);
		}
		catch (final XMPPException e) {
			processError();
			return;
		}

		if (!mXmppConnection.isConnected()) {
			processError();
			return;
		}
		
		processSuccess();
		
	}

	private void processError() {
		if (mListener != null) {
			mListener.onLoginResult(XMPP_LOGIN_FAIL, mAction);
		}
	}

	private void processSuccess() {
		if (mListener != null) {
			mListener.onLoginResult(XMPP_LOGIN_OK, mAction);
		}
	
	}

}
