package org.tacademy.network.rss.util;

import java.util.ArrayList;
import java.util.List;

import org.tacademy.network.rss.MyApplication;
import org.tacademy.network.rss.google.AccessToken;

import android.content.SharedPreferences;

public class PropertyManager {

	private final static String MY_SETTING_PREFS = "mypref";
	private SharedPreferences prefs;
	private SharedPreferences.Editor edit;
	
	private ArrayList<String> cookies = new ArrayList<String>();
	
	private final static String PREF_FIELD_CODE = "Code";
	private String code;

	private final static String PREF_FIELD_USER_NAME = "UserName";
	private String userName;
	
	private final static String PREF_FIELD_PASSWORD = "Password";
	private String password;
	
	private final static String PERF_FIELD_USER_ID = "UserId";
	private int userId = -1;

	private final static String PERF_FIELD_USER_ACCOUNT = "UserAccount";
	private String userAccount;
	
	private final static String PREF_FIELD_REGISTRATION_ID = "RegistrationId";
	private String registrationId;
	
	private final static String PREF_FIELD_UPLOAD_URL = "UploadURL";
	private String uploadUrl;
	
	private final static String PREF_FIELD_UPLOAD_TEST_URL = "UploadTestURL";
	private String uploadTestUrl;

	private final static String PREF_FIELD_UPLOAD_BOARD_INSERT_URL = "UploadBoardInsertURL";
	private String uploadBoardInsertUrl;

	private final static String PREF_FIELD_UPLOAD_BOARD_UPDATE_URL = "UploadBoardUpdateURL";
	private String uploadBoardUpdateUrl;
	
	private final static String PREF_FIELD_TOKEN_ACCESS_TOKEN = "AccessToken";
	private final static String PREF_FIELD_TOKEN_TOKEN_TYPE = "TokenType";
	private final static String PREF_FIELD_TOKEN_REFRESH_TOKEN = "RefreshToken";
	private AccessToken mToken;
	
	private final static String PREF_FIELD_GMAIL_FEED_AUTH_CODE = "GmailAuthCode";
	private String gmailFeedAuthCode;
	
	private static PropertyManager instance = null;
	
	public static PropertyManager getInstance() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}
	
	private PropertyManager() {
		prefs = MyApplication.getContext().getSharedPreferences(MY_SETTING_PREFS, 0);
		edit = prefs.edit();
	}
	
	public void setCookie(ArrayList<String> nCookies) {
		// cookies = setcookies;
		ArrayList<String> removeCookie = new ArrayList<String>();
		ArrayList<String> addCookie = new ArrayList<String>();
 		for (String nCookie : nCookies) {
			String cookie = nCookie.split(";")[0];
			String head = cookie.split("=")[0];
			for (String oCookie : cookies) {
				String ohead = oCookie.split("=")[0];
				if (head.equals(ohead)) {
					removeCookie.add(oCookie);
				}
			}
			addCookie.add(cookie);
		}
 		cookies.removeAll(removeCookie);
 		cookies.addAll(addCookie);
	}
	
	public ArrayList<String> getCookie() {
		return cookies;
	}
	
	public void setCode(String code) {
		this.code = code;
		edit.putString(PREF_FIELD_CODE, code);
		edit.commit();
	}
	
	public String getCode() {
		if (code != null) {
			return code;
		}
		code = prefs.getString(PREF_FIELD_CODE, "");
		return code;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
		edit.putString(PREF_FIELD_USER_NAME, userName);
		edit.commit();
	}
	
	public String getUserName() {
		if (userName != null) {
			return userName;
		}
		userName = prefs.getString(PREF_FIELD_USER_NAME, "");
		return userName;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
		edit.putInt(PERF_FIELD_USER_ID, userId);
		edit.commit();
	}
	
	public int getUserId() {
		if (userId != -1) {
			return userId;
		}
		userId = prefs.getInt(PERF_FIELD_USER_ID, -1);
		return userId;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
		edit.putString(PERF_FIELD_USER_ACCOUNT, userAccount);
		edit.commit();
	}
	
	public String getUserAccount() {
		if (userAccount != null) {
			return userAccount;
		}
		userAccount = prefs.getString(PERF_FIELD_USER_ACCOUNT, "");
		return userAccount;
	}

	public void setPassword(String password) {
		this.password = password;
		edit.putString(PREF_FIELD_PASSWORD, password);
		edit.commit();
	}
	
	public String getPassword() {
		if (password != null) {
			return password;
		}
		password = prefs.getString(PREF_FIELD_PASSWORD, "");
		return password;
	}
	
	public void setRegistrationId(String regid) {
		this.registrationId = regid;
		edit.putString(PREF_FIELD_REGISTRATION_ID, regid);
		edit.commit();
	}
	
	public String getRegistrationId() {
		if (registrationId != null) {
			return registrationId;
		}
		registrationId = prefs.getString(PREF_FIELD_REGISTRATION_ID, "");
		return registrationId;
	}
	
	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
		edit.putString(PREF_FIELD_UPLOAD_URL, uploadUrl);
		edit.commit();
	}
	
	public String getUploadUrl() {
		if (uploadUrl != null) {
			return uploadUrl;
		}
		return "";
//		uploadUrl = prefs.getString(PREF_FIELD_UPLOAD_URL, "");
//		return uploadUrl;
	}

	public void setUploadTestUrl(String uploadUrl) {
		this.uploadTestUrl = uploadUrl;
		edit.putString(PREF_FIELD_UPLOAD_TEST_URL, uploadUrl);
		edit.commit();
	}
	
	public String getUploadTestUrl() {
		if (uploadTestUrl != null) {
			return uploadTestUrl;
		}
		return "";
//		uploadTestUrl = prefs.getString(PREF_FIELD_UPLOAD_URL, "");
//		return uploadTestUrl;
	}

	public void setUploadBoardInsertUrl(String uploadUrl) {
		this.uploadBoardInsertUrl = uploadUrl;
		edit.putString(PREF_FIELD_UPLOAD_BOARD_INSERT_URL, uploadUrl);
		edit.commit();
	}
	
	public String getUploadBoardInsertUrl() {
		if (uploadBoardInsertUrl != null) {
			return uploadBoardInsertUrl;
		}
		return "";
//		uploadTestUrl = prefs.getString(PREF_FIELD_UPLOAD_URL, "");
//		return uploadTestUrl;
	}

	public void setUploadBoardUpdateUrl(String uploadUrl) {
		this.uploadBoardUpdateUrl = uploadUrl;
		edit.putString(PREF_FIELD_UPLOAD_BOARD_UPDATE_URL, uploadUrl);
		edit.commit();
	}
	
	public String getUploadBoardUpdateUrl() {
		if (uploadBoardUpdateUrl != null) {
			return uploadBoardUpdateUrl;
		}
		return "";
//		uploadTestUrl = prefs.getString(PREF_FIELD_UPLOAD_URL, "");
//		return uploadTestUrl;
	}

	public void setAccessToken(AccessToken token) {
		// TODO Auto-generated method stub
		mToken = token;
		edit.putString(PREF_FIELD_TOKEN_ACCESS_TOKEN, mToken.access_token);
		edit.putString(PREF_FIELD_TOKEN_TOKEN_TYPE, mToken.token_type);
		edit.putString(PREF_FIELD_TOKEN_REFRESH_TOKEN, mToken.refresh_token);
		edit.commit();
	}
	
	public AccessToken getAccessToken() {
		if (mToken != null) {
			return mToken;
		}
		String accessToken = prefs.getString(PREF_FIELD_TOKEN_ACCESS_TOKEN, null);
		String tokenType = prefs.getString(PREF_FIELD_TOKEN_TOKEN_TYPE, null);
		String refreshToken = prefs.getString(PREF_FIELD_TOKEN_REFRESH_TOKEN, null);
		if (accessToken != null) {
			mToken = new AccessToken();
			mToken.access_token = accessToken;
			mToken.token_type = tokenType;
			mToken.refresh_token = refreshToken;
		}
		return mToken;
	}

	public void setGmailFeedAuthorizationCode(String code) {
		// TODO Auto-generated method stub
		gmailFeedAuthCode = code;
		edit.putString(PREF_FIELD_GMAIL_FEED_AUTH_CODE, code);
		edit.commit();
	}
	
	public String getGmailFeedAuthorizationCode() {
		if (gmailFeedAuthCode != null) {
			return gmailFeedAuthCode;
		}
		gmailFeedAuthCode = prefs.getString(PREF_FIELD_GMAIL_FEED_AUTH_CODE, "");
		return gmailFeedAuthCode;
	}
}
