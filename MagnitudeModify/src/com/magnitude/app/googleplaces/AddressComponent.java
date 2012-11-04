package com.magnitude.app.googleplaces;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.os.Parcel;
import android.os.Parcelable;

import com.magnitude.app.parser.SaxParserHandler;
import com.magnitude.app.parser.SaxResultParser;


public class AddressComponent implements Parcelable, SaxParserHandler {
	public String long_name;
	public String short_name;
	public ArrayList<String> types = new ArrayList<String>();

	public AddressComponent() {
		
	}
	
	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "address_component";
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
		if (tagName.equalsIgnoreCase("long_name")) {
			long_name = (String)content;
		} else if (tagName.equalsIgnoreCase("short_name")) {
			short_name = (String)content;
		} else if (tagName.equalsIgnoreCase("type")) {
			types.add((String)content);
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
		out.writeString(long_name);
		out.writeString(short_name);
		out.writeStringList(types);
	}
	
	private AddressComponent(Parcel in) {
		long_name = in.readString();
		short_name = in.readString();
		types = new ArrayList<String>();
		in.readStringList(types);
	}
	
	public static Parcelable.Creator<AddressComponent> CREATOR = new Parcelable.Creator<AddressComponent>() {

		@Override
		public AddressComponent createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new AddressComponent(source);
		}

		@Override
		public AddressComponent[] newArray(int size) {
			// TODO Auto-generated method stub
			return new AddressComponent[size];
		}
	};
}
