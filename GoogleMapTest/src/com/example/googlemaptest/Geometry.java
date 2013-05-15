package com.example.googlemaptest;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.example.googlemaptest.parser.SaxParserHandler;
import com.example.googlemaptest.parser.SaxResultParser;

import android.os.Parcel;
import android.os.Parcelable;

public class Geometry implements Parcelable, SaxParserHandler {

	Location location;
	
	public Geometry() {
		
	}

	public Geometry(Parcel source) {
		// TODO Auto-generated constructor stub
		location = source.readParcelable(Location.class.getClassLoader());
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeParcelable(location, flags);
	}
	
	public static Creator<Geometry> CREATOR = new Creator<Geometry>() {

		@Override
		public Geometry createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Geometry(source);
		}

		@Override
		public Geometry[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Geometry[size];
		}
	};

	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "geometry";
	}

	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("location")) {
			Location location = new Location();
			parser.pushHandler(location);
		}
		
	}

	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("location")) {
			location = (Location)content;
		}
		
	}

	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}
}
