package com.example.googlemaptest;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.example.googlemaptest.parser.SaxParserHandler;
import com.example.googlemaptest.parser.SaxResultParser;

import android.os.Parcel;
import android.os.Parcelable;

public class GooglePlaceItem implements Parcelable, SaxParserHandler {

	Geometry geometry;
	String icon;
	String id;
	String name;
	String reference;
	ArrayList<String> types = new ArrayList<String>();
	String vicinity;
	
	public GooglePlaceItem() {
		
	}

	public GooglePlaceItem(Parcel source) {
		// TODO Auto-generated constructor stub
		geometry = source.readParcelable(Geometry.class.getClassLoader());
		icon = source.readString();
		id = source.readString();
		name = source.readString();
		reference = source.readString();
		types = new ArrayList<String>();
		source.readStringList(types);
		vicinity = source.readString();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeParcelable(geometry, flags);
		dest.writeString(icon);
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(reference);
		dest.writeStringList(types);
		dest.writeString(vicinity);
		
	}
	
	public static Creator<GooglePlaceItem> CREATOR = new Creator<GooglePlaceItem>() {

		@Override
		public GooglePlaceItem createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new GooglePlaceItem(source);
		}

		@Override
		public GooglePlaceItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new GooglePlaceItem[size];
		}
	};
	
	public String toString() {
		return name + "\n\n" + vicinity;
	}

	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "result";
	}

	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("geometry")) {
			Geometry geometry = new Geometry();
			parser.pushHandler(geometry);
		}
		
	}

	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("name")) {
			name = (String)content;
		} else if (tagName.equalsIgnoreCase("vicinity")) {
			vicinity = (String)content;
		} else if(tagName.equalsIgnoreCase("type")) {
			types.add((String)content);
		} else if (tagName.equalsIgnoreCase("geometry")) {
			geometry = (Geometry)content;
		}
 		
	}

	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	};
}
