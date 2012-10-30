package org.tacademy.network.rss.google;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GoogleConstants {
	public static final String ACCESS_TOKEN_URL = "https://accounts.google.com/o/oauth2/token";
	public static final String RESPONSE_TYPE="code";
	public static final String SCOPE="https://mail.google.com/mail/feed/atom";
	public static final String REDIRECT_URI="http://localhost";
	public static final String CLIENT_ID="972646024433.apps.googleusercontent.com";
	public static final String CLIENT_SECRET="By5eGC_7t7TkSd-tipfP3KdN";
	public static final String STATE="mail";
	// 1. SCOPE
	// 2. STATE
	// 3. REDIRECT_URI
	// 4. RESPONSE_TYPE
	// 5. CLIENT_ID
	public static final String AUTHENTICATION_ENDPOINT_URL = "https://accounts.google.com/o/oauth2/auth?scope=%s&state=%s&redirect_uri=%s&response_type=%s&client_id=%s";

	public static final String FIELD_NAME_CODE="code";
	public static final String FIELD_NAME_STATE="state";
	
	public static final String USER_AGENT="MyAndroidGoogleApp";
	public static final String GRANT_TYPE_AUTH_CODE ="authorization_code";
	public static final String GRANT_TYPE_REFRESH = "refresh_token";

	public static String getAuthenticationEndPoint() {
		String url = null;
        try {
			url = String.format(AUTHENTICATION_ENDPOINT_URL, 
					URLEncoder.encode(SCOPE, "UTF-8"),
					STATE,
					URLEncoder.encode(REDIRECT_URI,"UTF-8"),
					RESPONSE_TYPE,
					CLIENT_ID);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
}
