package org.tacademy.network.rss;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

	public static MyApplication mContext;
	public MyApplication() {
		super();
		mContext = this;
		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
	}
	
	public static Context getContext() {
		return mContext;
	}

}
