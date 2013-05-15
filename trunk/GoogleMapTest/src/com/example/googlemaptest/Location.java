package com.example.googlemaptest;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.example.googlemaptest.parser.SaxParserHandler;
import com.example.googlemaptest.parser.SaxResultParser;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable,SaxParserHandler {
	double lat;
	double lng;
	
	public Location() {
		
	}
	
	public Location(Parcel source) {
		// TODO Auto-generated constructor stub
		lat = source.readDouble();
		lng = source.readDouble();
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeDouble(lat);
		dest.writeDouble(lng);
	}
	
	public static Creator<Location> CREATOR = new Creator<Location>() {

		@Override
		public Location createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Location(source);
		}

		@Override
		public Location[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Location[size];
		}
	};

	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "location";
	}

	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("lat")) {
			lat = Double.parseDouble((String)content);
		} else if (tagName.equalsIgnoreCase("lng")) {
			lng = Double.parseDouble((String)content);
		}
	}

	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}
}
