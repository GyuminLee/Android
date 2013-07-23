package com.example.samplegsontest;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DataObject2 implements JsonSerializer<DataObject2>, JsonDeserializer<DataObject2> {

	String name;
	int age;
	DateTime time;
	
	@Override
	public DataObject2 deserialize(JsonElement element, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		// TODO Auto-generated method stub
		JsonObject jobj = (JsonObject)element;
		DataObject2 obj = new DataObject2();
		obj.name = jobj.getAsJsonPrimitive("name").getAsString();
		obj.age = jobj.getAsJsonPrimitive("age").getAsInt();
		obj.time = new DateTime(jobj.getAsJsonPrimitive("time").getAsString());
		return obj;
	}

	@Override
	public JsonElement serialize(DataObject2 object, Type type,
			JsonSerializationContext context) {
		JsonObject obj = new JsonObject();
		obj.addProperty("name", name);
		obj.addProperty("age", age);
		obj.addProperty("time", time.toString());
		return obj;
	}

}
