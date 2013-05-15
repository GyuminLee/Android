package com.example.googlemaptest.parser;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;

public class GsonResultParser<T> extends InputStreamParser {

	T result;
	Class clazz;
	
	public GsonResultParser(Class clazz) {
		this.clazz = clazz;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void doParse(InputStream is) throws InputStreamParserException {
		// TODO Auto-generated method stub
		InputStreamReader isr = new InputStreamReader(is);
		result = (T) getGson().fromJson(isr,clazz);
	}
	
	

	protected Gson getGson() {
		return new Gson();
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return result;
	}

}
