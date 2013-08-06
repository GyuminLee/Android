package com.example.samplenavermovies.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class JSONResultParser extends InputStreamParser {

	@Override
	public void doParse(InputStream is) throws InputStreamParserException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String str;
		try {
			while((str = br.readLine()) != null) {
				sb.append(str).append("\n\r");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new InputStreamParserException();
		}
		try {
			JSONObject jObject = new JSONObject(sb.toString());
			parseJsonRoot(jObject);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new InputStreamParserException();
		}
	}
	
	public abstract void parseJsonRoot(JSONObject jObject) throws JSONException;

}
