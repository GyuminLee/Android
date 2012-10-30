package org.tacademy.network.rss.chat;

import java.io.IOException;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.tacademy.network.rss.util.PropertyManager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class XMPPLoginManager implements Runnable {

	public static final int XMPP_LOGIN_OK = 1;
	public static final int XMPP_LOGIN_FAIL = 2;
	public static final String ACCOUNT_TYPE = "com.google";
	public static final String AUTH_TOKEN_TYPE = "mail";
	private static final String DOMAIN = "talk.google.com";
	
	public static final int XMPP_ACTION_LOGIN = 1;
	public static final int XMPP_ACTION_LOGOUT = 2;
	
	XMPPConnection mXmppConnection;
	String mAuthToken;
	AccountManager mAccountManager;
	String mAccountName;
	Handler mHandler;
	Activity mActivity;
	private boolean isLogin = false;
	private boolean isRetry = false;
	int mAction;
	
	public interface OnXMPPLoginListener {
		public void onLoginComplete(int result,String name, int type, XMPPConnection connection);
	}
	
	OnXMPPLoginListener mListener;
	
	public void setOnXMPPLoginListener(OnXMPPLoginListener listener) {
		mListener = listener;
	}
	
	private static XMPPLoginManager instance;
	
	public static XMPPLoginManager getInstance() {
		if (instance == null) {
			instance = new XMPPLoginManager();
		}
		return instance;
	}
	
	private XMPPLoginManager() {
		mHandler = new Handler();
	}

	public XMPPConnection getXMPPConnection() {
		return mXmppConnection;
	}
	
	public boolean isLogin() {
		return isLogin;
	}

	public void invalidate() {
		mAccountManager.invalidateAuthToken(ACCOUNT_TYPE, mAuthToken);
		isLogin = false;
	}
	
	public void startLogin(String accountName,Activity activity,OnXMPPLoginListener listener) {
		isRetry = false;
		mAccountName = accountName;
		mActivity = activity;
		mAccountManager = AccountManager.get(mActivity);
		mListener = listener;
		mAction = XMPP_ACTION_LOGIN;
		getAuthToken();
	}

	public void startAutoLogin(Activity activity,OnXMPPLoginListener listener) {
		if (isAutoLogin()) {
			String name = PropertyManager.getInstance().getUserAccount();
			startLogin(name,activity,listener);
		}
	}
	
	public void startLogout() {
		mAction = XMPP_ACTION_LOGOUT;
		postLogin();
	}
	
	public boolean isAutoLogin() {
		boolean isAutoLogin = true;
		String name = PropertyManager.getInstance().getUserAccount();
		if (name.equals("")) {
			isAutoLogin = false;
		}
		return isAutoLogin;
	}
	
	private void getAuthToken() {
		// TODO Auto-generated method stub
        Account account = new Account(mAccountName,ACCOUNT_TYPE);
        mAccountManager.getAuthToken(account, AUTH_TOKEN_TYPE, null, mActivity, new AccountManagerCallback<Bundle>() {

			public void run(AccountManagerFuture<Bundle> future) {
				Bundle b;
				try {
					b = future.getResult();
					mAuthToken = b.getString(AccountManager.KEY_AUTHTOKEN);
					postLogin();
					return;
				} catch (OperationCanceledException e) {
					e.printStackTrace();
				} catch (AuthenticatorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				postError();
			}
        	
        }, mHandler);		
		
	}

	protected void postError() {
		// TODO Auto-generated method stub
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mListener != null) {
					mListener.onLoginComplete(XMPP_LOGIN_FAIL, mAccountName, mAction, null);
				}
			}
			
		});
	}

	protected void postLogin() {
		// TODO Auto-generated method stub
		Thread th = new Thread(this);
		th.start();
	}

	private void againLogin() {
		isRetry = true;
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mAccountManager.invalidateAuthToken(ACCOUNT_TYPE, mAuthToken);
				getAuthToken();				
			}
			
		});
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (mAction == XMPP_ACTION_LOGIN) {
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
		} else if (mAction == XMPP_ACTION_LOGOUT ){
			// logout Ã³¸®.
		}

	}

	private void processSuccess() {
		// TODO Auto-generated method stub
		PropertyManager.getInstance().setUserAccount(mAccountName);
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mListener != null) {
					mListener.onLoginComplete(XMPP_LOGIN_OK, mAccountName, mAction, mXmppConnection);
				}
			}
			
		});
	}

	private void processError() {
		// TODO Auto-generated method stub
		if (isRetry == false) {
			isRetry = true;
			againLogin();
		} else {
			postError();
		}
	}

}
