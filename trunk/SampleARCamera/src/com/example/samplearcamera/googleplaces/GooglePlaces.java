package com.example.samplearcamera.googleplaces;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.samplearcamera.parser.SaxParserHandler;
import com.example.samplearcamera.parser.SaxResultParser;

public class GooglePlaces implements Parcelable, SaxParserHandler {
	public String status;
	public ArrayList<String> http_attrs = new ArrayList<String>();
	public ArrayList<GooglePlaceItem> items = new ArrayList<GooglePlaceItem>();
	
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
			http_attrs.add((String)content);
		} else if (tagName.equalsIgnoreCase("result")) {
			items.add((GooglePlaceItem)content);
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
		out.writeTypedList(items);
	}
	
	private GooglePlaces(Parcel in) {
		items = new ArrayList<GooglePlaceItem>();
		in.readTypedList(items, GooglePlaceItem.CREATOR);
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
