package com.example.samplenavermovies.model;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

public class GoogleAccessTokenRequest extends FormRequest {

	public GoogleAccessTokenRequest(String code, String redirect, String clientId, String clientSecret, String scope) {
		addFormData("code", code);
		addFormData("redirect_uri", redirect);
		addFormData("client_id", clientId);
		addFormData("scope",scope);
		addFormData("client_secret",clientSecret);
		addFormData("grant_type","authorization_code");
	}
	
	
	@Override
	public URL getURL() {
		// TODO Auto-generated method stub
		try {
			return new URL("https://accounts.google.com/o/oauth2/token");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	Object parse(InputStream is) {
		
		InputStreamReader isr = new InputStreamReader(is);
		Gson gson = new Gson();
		GoogleAccessToken token = gson.fromJson(isr, GoogleAccessToken.class);
		return token;
	}

}
