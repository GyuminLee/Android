package org.tacademy.basic.googleplaces.placelist;

import java.util.ArrayList;

import org.tacademy.basic.googleplaces.parser.SaxParserHandler;
import org.tacademy.basic.googleplaces.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.os.Parcel;
import android.os.Parcelable;

public class GooglePlaces implements Parcelable, SaxParserHandler {
	public String status;
	public ArrayList<String> html_attributions = new ArrayList<String>();
	public ArrayList<GooglePlaceItem> results = new ArrayList<GooglePlaceItem>();
	
	public GooglePlaces() {
		
	}
	
	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "PlaceSearchResponse";
	}

	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("result")) {
			GooglePlaceItem item = new GooglePlaceItem("result");
			parser.pushHandler(item);
		}
		
	}

	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("status")) {
			status = (String)content;
		} else if (tagName.equalsIgnoreCase("http_attribution")) {
			html_attributions.add((String)content);
		} else if (tagName.equalsIgnoreCase("result")) {
			results.add((GooglePlaceItem)content);
		}
		
	}

	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeTypedList(results);
	}
	
	private GooglePlaces(Parcel in) {
		results = new ArrayList<GooglePlaceItem>();
		in.readTypedList(results, GooglePlaceItem.CREATOR);
	}
	
	public static Parcelable.Creator<GooglePlaces> CREATOR = new Parcelable.Creator<GooglePlaces>() {

		@Override
		public GooglePlaces createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new GooglePlaces(source);
		}

		@Override
		public GooglePlaces[] newArray(int size) {
			// TODO Auto-generated method stub
			return new GooglePlaces[size];
		}
	};
}
