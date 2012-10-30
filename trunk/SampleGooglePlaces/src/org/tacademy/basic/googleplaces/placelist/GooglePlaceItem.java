package org.tacademy.basic.googleplaces.placelist;

import java.util.ArrayList;

import org.tacademy.basic.googleplaces.parser.SaxParserHandler;
import org.tacademy.basic.googleplaces.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.os.Parcel;
import android.os.Parcelable;

public class GooglePlaceItem implements Parcelable, SaxParserHandler {
	public String name;
	public String vicinity;
	public ArrayList<String> types = new ArrayList<String>();
	public Geometry geometry;
	public String icon;
	public String reference;
	public String id;
	public float rating;
	public ArrayList<GoogleEvent> events = new ArrayList<GoogleEvent>();
	
	private String tag = "result";
	
	public GooglePlaceItem() {
		
	}
	
	public GooglePlaceItem(String tag) {
		this.tag = tag;
	}
	
	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return tag;
	}

	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("geometry")) {
			Geometry geometry = new Geometry();
			parser.pushHandler(geometry);
		} else if (tagName.equalsIgnoreCase("event")) {
			GoogleEvent event = new GoogleEvent();
			parser.pushHandler(event);
		}
	}

	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("name")) {
			name = (String)content;
		} else if (tagName.equalsIgnoreCase("icon")) {
			icon = (String)content;
		} else if (tagName.equalsIgnoreCase("vicinity")) {
			vicinity = (String)content;
		} else if (tagName.equalsIgnoreCase("type")) {
			types.add((String)content);
		} else if (tagName.equalsIgnoreCase("geometry")) {
			geometry = (Geometry)content;
		} else if (tagName.equalsIgnoreCase("rating")) {
			rating = Float.parseFloat((String)content);
		} else if (tagName.equalsIgnoreCase("id")) {
			id = (String)content;
		} else if (tagName.equalsIgnoreCase("reference")) {
			reference = (String)content;
		} else if (tagName.equalsIgnoreCase("event")) {
			events.add((GoogleEvent)content);
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
		out.writeString(name);
		out.writeString(vicinity);
		out.writeStringList(types);
		out.writeParcelable(geometry, flags);
		out.writeString(icon);
		out.writeString(reference);
		out.writeString(id);
		out.writeFloat(rating);
		out.writeTypedList(events);
	}
	
	private GooglePlaceItem(Parcel in) {
		name = in.readString();
		vicinity = in.readString();
		types = new ArrayList<String>();
		in.readStringList(types);
		geometry = in.readParcelable(Geometry.class.getClassLoader());
		icon = in.readString();
		reference = in.readString();
		id = in.readString();
		rating = in.readFloat();
		events = new ArrayList<GoogleEvent>();
		in.readTypedList(events, GoogleEvent.CREATOR);
	}

	public static Parcelable.Creator<GooglePlaceItem> CREATOR = new Parcelable.Creator<GooglePlaceItem>() {

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
}
