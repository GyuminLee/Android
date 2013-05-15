package com.example.googlemaptest.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.example.googlemaptest.util.PropertyManager;

public class LoginManager implements Runnable {

	public static final int RESULT_LOGIN_OK = 1;
	public static final int RESULT_LOGIN_FAIL = -1;
	public static final int ACTION_LOGIN = 1;
	public static final int ACTION_LOGOUT = 2;
	
	public static final String ACCOUNT_TYPE = "com.google";
	
	private static final int RESULT_OK = 1;
	private static final int RESULT_INVALID_AUTHTOKEN = 2;
	private static final int RESULT_FAIL = 3;
	
	private static final String BASE_URL = NetworkRequest.BASE_URL;
	private static final String AUTH_TOKEN_TYPE = "ah";

	private static final String AUTH_URL = BASE_URL + "/_ah/login";
	
	private static final String LOGIN_URL_FORMAT = AUTH_URL + "?continue=%s&auth=%s";

	private static final String LOGOUT_URL_FORMAT = AUTH_URL + "?continue=%s&action=Logout";
	
	private Activity mActivity;
	private AccountManager mAccountManager;
	
	private Handler mHandler;
	private String mAuthToken;
	private String mName;
	private boolean isRetry = false;
	private boolean isWait = true;
	int mAction;
	
	private boolean isLogin = false;
	
	private static LoginManager instance = null;
	
	public interface OnLoginResultListener {
		public void onLoginResult(int result,String name,int action);
	}
	
	private OnLoginResultListener mListener;
	
	public void setOnLoginResultListener(OnLoginResultListener listener) {
		mListener = listener;
	}

	public ArrayList<String> getAccount(Context context) {
		AccountManager accountManager = AccountManager.get(context);
		Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
		ArrayList<String> names = new ArrayList<String>();
		for (Account account : accounts) {
			names.add(account.name);
		}
		return names;
	}
	
	public static LoginManager getInstance() {
		if (instance == null) {
			instance = new LoginManager();
		}
		return instance;
	}
	
	private LoginManager() {
		mHandler = new Handler();
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void invalidate() {
		mAccountManager.invalidateAuthToken(ACCOUNT_TYPE, mAuthToken);
		isLogin = false;
	}
	
	public void startLogin(String name, Activity activity,OnLoginResultListener listener) {
		isRetry = false;
		mName = name;
		mActivity = activity;
		mAccountManager = AccountManager.get(mActivity);
		mAction = ACTION_LOGIN;
		mListener = listener;
		getAuthToken();
	}

	public void startAutoLogin(Activity activity,OnLoginResultListener listener) {
		if (isAutoLogin()) {
			String name = PropertyManager.getInstance().getUserAccount();
			startLogin(name,activity,listener);
		}
	}
	
	public boolean isAutoLogin() {
		boolean isAutoLogin = true;
		String name = PropertyManager.getInstance().getUserAccount();
		if (name.equals("")) {
			isAutoLogin = false;
		}
		return isAutoLogin;
	}
	
	public void startLogout(OnLoginResultListener listener) {
		if (isLogin) {
			mName = PropertyManager.getInstance().getUploadUrl();
			mAction = ACTION_LOGOUT;
			mListener = listener;
			postLogin();
		}
	}
	
	private void getAuthToken() {
        Account account = new Account(mName,ACCOUNT_TYPE);
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
					mListener.onLoginResult(RESULT_LOGIN_FAIL, mName, mAction);
				}
			}
			
		});
	}

	private void againLogin() {
		isRetry = true;
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mAction == ACTION_LOGIN) {
					mAccountManager.invalidateAuthToken(ACCOUNT_TYPE, mAuthToken);
					getAuthToken();
				} else {
					postLogin();
				}
				
			}
			
		});
	}

	
	private synchronized void postLogin() {
		Thread th = new Thread(this);
		th.start();
	}	
	
	public void run() {
		// TODO Auto-generated method stub
		int result = doProcess();
		if (result == RESULT_OK) {
			reportSuccess();
		} else if (result == RESULT_FAIL) {
			reportFail();
		} else if (result == RESULT_INVALID_AUTHTOKEN) {
			if (isRetry) {
				reportFail();
			} else {
				againLogin();
			}
		}
			
	}
	
	private int doProcess() {
	    DefaultHttpClient client = new DefaultHttpClient();
	    try {
	    	URI uri = null;
	    	if (mAction == ACTION_LOGIN) {
	    		uri = new URI(String.format(LOGIN_URL_FORMAT, URLEncoder.encode(BASE_URL, "UTF-8"),mAuthToken));
	    	} else if (mAction == ACTION_LOGOUT) {
	    		uri = new URI(String.format(LOGOUT_URL_FORMAT, URLEncoder.encode(BASE_URL, "UTF-8")));
	    	}

		    HttpGet method = new HttpGet(uri);
		    final HttpParams getParams = new BasicHttpParams();
		    HttpClientParams.setRedirecting(getParams, false);  // continue is not used
		    method.setParams(getParams);
		
		    HttpResponse res = client.execute(method);
		    Header[] headers = res.getHeaders("Set-Cookie");
		    ArrayList<String> cookies = new ArrayList<String>();
		    if (res.getStatusLine().getStatusCode() != 302 ||
		            headers.length == 0) {
		    	return RESULT_INVALID_AUTHTOKEN;
		    }
		    for (Header header : headers) {
		    	cookies.add(header.getValue().split(";", 2)[0]);
		    }
		    PropertyManager.getInstance().setCookie(cookies);
	    	return RESULT_OK;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return RESULT_FAIL;
	}

	private void reportSuccess() {
		// TODO Auto-generated method stub
		if (mAction == ACTION_LOGIN) {
			isLogin = true;
			PropertyManager.getInstance().setUserAccount(mName);
		} else {
			isLogin = false;
			PropertyManager.getInstance().setUserAccount("");
		}
		
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mListener != null) {
					mListener.onLoginResult(RESULT_LOGIN_OK,mName,mAction);
				}
			}
			
		});
	}

	private void reportFail() {
		// TODO Auto-generated method stub
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mListener != null) {
					mListener.onLoginResult(RESULT_LOGIN_FAIL,mName,mAction);
				}
			}
			
		});
	}

}
