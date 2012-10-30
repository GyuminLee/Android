package org.tacademy.network.rss.google;

import org.json.JSONException;
import org.json.JSONObject;
import org.tacademy.network.rss.parser.JSONResultParser;

public class RefreshTokenParser extends JSONResultParser {

	AccessToken token;
	@Override
	public void parseJsonRoot(JSONObject jObject) throws JSONException {
		// TODO Auto-generated method stub
		token = new AccessToken();
		token.access_token = jObject.getString("access_token");
		token.token_type = jObject.getString("token_type");
		token.expires_in = jObject.getString("expires_in");
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return token;
	}

}
