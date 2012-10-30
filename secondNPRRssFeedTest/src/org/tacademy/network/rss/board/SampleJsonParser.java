package org.tacademy.network.rss.board;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tacademy.network.rss.parser.JSONResultParser;

public class SampleJsonParser extends JSONResultParser {

	ContentData data = new ContentData();
	
	@Override
	public void parseJsonRoot(JSONObject jObject) throws JSONException {
		// TODO Auto-generated method stub
		JSONArray cons = jObject.getJSONArray("contents");
		for (int i = 0; i < cons.length(); i++) {
			JSONObject con = cons.getJSONObject(i);
			Content content = parseContent(con);
			data.contents.add(content);
		}
		
		JSONArray carName = jObject.getJSONArray("searchCarName");
		for (int i = 0; i < carName.length(); i++) {
			JSONObject carN = carName.getJSONObject(i);
			data.searchCarNames.add(carN.getString("carName"));
		}
		
		// ...

	}

	private Content parseContent(JSONObject con) throws JSONException{
		// TODO Auto-generated method stub
		Content content = new Content();
		content.carNameId = con.getString("carNameId");
		content.carYearId = con.getString("carYearId");
		//..
		
		return content;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return data;
	}

}
