package org.tacademy.basic.authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

/**
 * @author Administrator
 *
 */
public class Authenticator extends AbstractAccountAuthenticator {

	Context mContext;
	
	public static final String GET_REFRESHTOKEN_URL = "https://accounts.google.com//o/oauth2/token";

	private String mAccessToken;
	
	public Authenticator(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	/* (non-Javadoc)
	 * @see android.accounts.AbstractAccountAuthenticator#addAccount(android.accounts.AccountAuthenticatorResponse, java.lang.String, java.lang.String, java.lang.String[], android.os.Bundle)
	 * Settings의 account & sync에서 계정을 선택시 호출.
	 * Account를 추가하기 위한 Activity를 호출하기 위한 Intent를 만들어서 Bundle객체에 담아서 넘겨줌.
	 */
	@Override
	public Bundle addAccount(AccountAuthenticatorResponse response,
			String accountType, String authTokenType,
			String[] requiredFeatures, Bundle options)
			throws NetworkErrorException {
		// TODO Auto-generated method stub
		Intent i = new Intent(mContext,AuthenticatorActivity.class);
		i.putExtra(AuthenticatorActivity.PARAM_AUTHTOKEN_TYPE, authTokenType);
		i.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
		Bundle b = new Bundle();
		b.putParcelable(AccountManager.KEY_INTENT, i);
		return b;
	}

	@Override
	public Bundle confirmCredentials(AccountAuthenticatorResponse response,
			Account account, Bundle options) throws NetworkErrorException {
		// TODO Auto-generated method stub
        if (options != null && options.containsKey(AccountManager.KEY_PASSWORD)) {
            final String password =
                options.getString(AccountManager.KEY_PASSWORD);
            final boolean verified = this.getAccessTokenFromGoogle(password);
            final Bundle result = new Bundle();
            result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, verified);
            return result;
        }
        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
        intent.putExtra(AuthenticatorActivity.PARAM_CONFIRMCREDENTIALS, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,
            response);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
        
	}

	@Override
	public Bundle editProperties(AccountAuthenticatorResponse response,
			String accountType) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Bundle getAuthToken(AccountAuthenticatorResponse response,
			Account account, String authTokenType, Bundle options)
			throws NetworkErrorException {
		// TODO Auto-generated method stub
        if (!authTokenType.equals(Constants.AUTHTOKEN_TYPE)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE,
                "invalid authTokenType");
            return result;
        }

        final Bundle result = new Bundle();
        
        String name = account.name;
        AccountManager am = AccountManager.get(mContext);
        String password = am.getPassword(account);
        boolean isSuccess = getAccessTokenFromGoogle(password);
        if (isSuccess) {
	        result.putString(AccountManager.KEY_ACCOUNT_NAME, name);
	        result.putString(AccountManager.KEY_ACCOUNT_TYPE,
	            Constants.AUTHTOKEN_TYPE);
	        result.putString(AccountManager.KEY_AUTHTOKEN, "authtoken");
	        return result;
        }
        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
        intent.putExtra(AuthenticatorActivity.PARAM_AUTHTOKEN_TYPE,
            authTokenType);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,
            response);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
	}

	@Override
	public String getAuthTokenLabel(String authTokenType) {
		// TODO Auto-generated method stub
		return "SampleAuthenticator";
	}

	@Override
	public Bundle hasFeatures(AccountAuthenticatorResponse response,
			Account account, String[] features) throws NetworkErrorException {
		// TODO Auto-generated method stub
        final Bundle result = new Bundle();
        result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false);
        return result;
	}

	@Override
	public Bundle updateCredentials(AccountAuthenticatorResponse response,
			Account account, String authTokenType, Bundle options)
			throws NetworkErrorException {
		// TODO Auto-generated method stub
		Intent i = new Intent(mContext,AuthenticatorActivity.class);
		i.putExtra(AuthenticatorActivity.PARAM_AUTHTOKEN_TYPE, authTokenType);
		i.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
		Bundle b = new Bundle();
		b.putParcelable(AccountManager.KEY_INTENT, i);
		return b;
	}

	
	public boolean getAccessTokenFromGoogle(String refreshToken) {
		try {
			URL url = new URL(GET_REFRESHTOKEN_URL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("user-agent", AuthenticatorActivity.USER_AGENT);
			OutputStream os = conn.getOutputStream();
			if (refreshToken == null) {
				return false;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("client_secret").append("=").append(AuthenticatorActivity.CLIENT_SECRET);
			sb.append("&");
			sb.append("grant_type").append("=").append("refresh_token");
			sb.append("&");
			sb.append("refresh_token").append("=").append(refreshToken);
			sb.append("&");
			sb.append("client_id").append("=").append(AuthenticatorActivity.CLIENT_ID);
			os.write(sb.toString().getBytes("UTF-8"));
			os.close();
			
			int respCode = conn.getResponseCode();
			if (respCode == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				parseInputStream(is);
				is.close();
				return true;
			} else {
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void parseInputStream(InputStream is) throws IOException , JSONException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String str = null;
		while((str = br.readLine()) != null) {
			sb.append(str).append("\n\r");
		}
		
		JSONObject jObject = new JSONObject(sb.toString());
		final String access_token = jObject.getString("access_token");
		final String token_type = jObject.getString("token_type");
		
		mAccessToken = access_token;
	}
	
}
