package org.mixare.data.convert;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mixare.POIMarker;
import org.mixare.data.DataHandler;
import org.mixare.data.DataSource;
import org.mixare.lib.marker.Marker;

public class GoogleDataProcessor extends DataHandler implements DataProcessor {

	@Override
	public String[] getUrlMatch() {
		String[] str = {"maps.googleapis.com"}; 
		return str;
	}

	@Override
	public String[] getDataMatch() {
		String[] str = {"results"};
		return str;
	}

	@Override
	public boolean matchesRequiredType(String type) {
		if(type.equals(DataSource.TYPE.GOOGLE.name())){
			return true;
		}
		return false;
	}

	@Override
	public List<Marker> load(String rawData, int taskId, int colour)
			throws JSONException {
		List<Marker> markers = new ArrayList<Marker>();
		JSONObject jObject = new JSONObject(rawData);
		JSONArray jResults = jObject.getJSONArray("results");
		for (int i = 0; i < jResults.length(); i++) {
			JSONObject poiObject = jResults.getJSONObject(i);
			String title = poiObject.getString("name");
			String id = poiObject.getString("id");
			JSONObject location = poiObject.getJSONObject("geometry").getJSONObject("location");
			double lat = location.getDouble("lat");
			double lng = location.getDouble("lng");
			double alt = 0.0;
			String reference = poiObject.getString("reference");
			String url = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyCe72iAyKd1mZ40jpa8v4vnFEG_Z-V6hFY&sensor=false&reference="+reference;
			Marker ma = new POIMarker(id, title, lat, lng, alt, url, taskId, colour);
			markers.add(ma);
		}
		return markers;
	}

}
