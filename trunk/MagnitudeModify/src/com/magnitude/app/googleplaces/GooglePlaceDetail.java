package com.magnitude.app.googleplaces;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.os.Parcel;
import android.os.Parcelable;

import com.magnitude.app.parser.SaxParserHandler;
import com.magnitude.app.parser.SaxResultParser;

public class GooglePlaceDetail implements Parcelable, SaxParserHandler {
	public String status;
	public String name;
	public String icon;
	public String vicinity;
	public ArrayList<String> types = new ArrayList<String>();
	public Geometry geometry;
	public float rating;
	public String id;
	public String reference;
	public ArrayList<GoogleEvent> events = new ArrayList<GoogleEvent>();
	public String formatted_phone_number;
	public String formatted_address;
	public String international_phone_number;
	public String url;
	public String website;
	public ArrayList<AddressComponent> address_components = new ArrayList<AddressComponent>();

	public GooglePlaceDetail() {
		
	}
	
	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "PlaceDetailsResponse";
	}

	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("geometry")) {
			Geometry geo = new Geometry("geometry");
			parser.pushHandler(geo);
		} else if (tagName.equalsIgnoreCase("event")) {
			GoogleEvent event = new GoogleEvent("event");
			parser.pushHandler(event);
		} else if (tagName.equalsIgnoreCase("address_component")) {
			AddressComponent address = new AddressComponent();
			parser.pushHandler(address);
		}
	}

	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("status")) {
			status = (String)content;
		} else if (tagName.equalsIgnoreCase("name")) {
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
		} else if (tagName.equalsIgnoreCase("formatted_phone_number")) {
			formatted_phone_number = (String)content;
		} else if (tagName.equalsIgnoreCase("formatted_address")) {
			formatted_address = (String)content;
		} else if (tagName.equalsIgnoreCase("international_phone_number")) {
			international_phone_number = (String)content;
		} else if (tagName.equalsIgnoreCase("url")) {
			url = (String)content;
		} else if (tagName.equalsIgnoreCase("website")) {
			website = (String)content;
		} else if (tagName.equalsIgnoreCase("address_component")) {
			address_components.add((AddressComponent)content);
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
		out.writeString(status);
		out.writeString(name);
		out.writeString(icon);
		out.writeString(vicinity);
		out.writeStringList(types);
		out.writeParcelable(geometry, flags);
		out.writeFloat(rating);
		out.writeString(id);
		out.writeString(reference);
		out.writeTypedList(events);
		out.writeString(formatted_phone_number);
		out.writeString(formatted_address);
		out.writeString(international_phone_number);
		out.writeString(url);
		out.writeString(website);
		out.writeTypedList(address_components);
	}
	
	private GooglePlaceDetail(Parcel in) {
		status = in.readString();
		name = in.readString();
		icon = in.readString();
		vicinity = in.readString();
		types = new ArrayList<String>();
		in.readStringList(types);
		geometry = in.readParcelable(Geometry.class.getClassLoader());
		rating = in.readFloat();
		id = in.readString();
		reference = in.readString();
		events = new ArrayList<GoogleEvent>();
		in.readTypedList(events, GoogleEvent.CREATOR);
		formatted_phone_number = in.readString();
		formatted_address = in.readString();
		international_phone_number = in.readString();
		url = in.readString();
		website = in.readString();
		address_components = new ArrayList<AddressComponent>();
		in.readTypedList(address_components, AddressComponent.CREATOR);
	}
	
	public static Parcelable.Creator<GooglePlaceDetail> CREATOR = new Parcelable.Creator<GooglePlaceDetail>() {

		@Override
		public GooglePlaceDetail createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new GooglePlaceDetail(source);
		}

		@Override
		public GooglePlaceDetail[] newArray(int size) {
			// TODO Auto-generated method stub
			return new GooglePlaceDetail[size];
		}
	};

}
