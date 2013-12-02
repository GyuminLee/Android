package com.example.hellotapi;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GeometryDeseriaizer implements JsonDeserializer<Geometry> {

	@Override
	public Geometry deserialize(JsonElement element, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		Geometry geometry = new Geometry();
		JsonObject object = element.getAsJsonObject();
		geometry.type = object.get("type").getAsString();
		if (geometry.type.equals("Point")) {
			geometry.coordinates = new double[1][2];
			JsonArray array = object.getAsJsonArray("coordinates");
			geometry.coordinates[0][0] = array.get(0).getAsDouble();
			geometry.coordinates[0][1] = array.get(1).getAsDouble();
		} else if (geometry.type.equals("LineString")) {
			JsonArray array = object.getAsJsonArray("coordinates");
			geometry.coordinates = new double[array.size()][2];
			for (int i = 0; i < array.size(); i++) {
				geometry.coordinates[i][0] = array.get(i).getAsJsonArray().get(0).getAsDouble();
				geometry.coordinates[i][1] = array.get(i).getAsJsonArray().get(1).getAsDouble();
			}
		}
		return geometry;
	}

}
