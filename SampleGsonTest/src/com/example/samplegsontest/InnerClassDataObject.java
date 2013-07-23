package com.example.samplegsontest;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

public class InnerClassDataObject implements InstanceCreator<InnerClassDataObject.DataObject> {

	public void parseJson() {
		Gson gson = new GsonBuilder().registerTypeAdapter(InnerClassDataObject.DataObject.class, this).create();
		String jsonText = "{\"a\":\"value1\",\"b\":\"value2\"";
		DataObject obj = gson.fromJson(jsonText, DataObject.class);
	}
	
	public class DataObject {
		String a;
		String b;
	}

	@Override
	public DataObject createInstance(Type type) {
		return new DataObject();
	}
}
