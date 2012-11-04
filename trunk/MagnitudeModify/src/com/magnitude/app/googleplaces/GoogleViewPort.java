package com.magnitude.app.googleplaces;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.os.Parcel;
import android.os.Parcelable;

import com.magnitude.app.parser.SaxParserHandler;
import com.magnitude.app.parser.SaxResultParser;


public class GoogleViewPort implements Parcelable, SaxParserHandler {
	public GoogleLocation northeast;
	public GoogleLocation southwest;
	
	private String tag = "viewport";
	
	public GoogleViewPort() {
		
	}
	
	public GoogleViewPort(String tag) {
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
		if (tagName.equalsIgnoreCase("northeast")) {
			GoogleLocation location = new GoogleLocation("northeast");
			parser.pushHandler(location);
		} else if (tagName.equalsIgnoreCase("southwest")) {
			GoogleLocation location = new GoogleLocation("southwest");
			parser.pushHandler(location);
		}
	}
	
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("northeast")) {
			this.northeast = (GoogleLocation)content;
		} else if (tagName.equalsIgnoreCase("southwest")) {
			this.southwest = (GoogleLocation)content;
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
		out.writeParcelable(northeast, flags);
		out.writeParcelable(southwest, flags);
	}
	
	private GoogleViewPort(Parcel in) {
		// TODO Auto-generated constructor stub
		northeast = in.readParcelable(GoogleLocation.class.getClassLoader());
		southwest = in.readParcelable(GoogleLocation.class.getClassLoader());
	}

	public static Parcelable.Creator<GoogleViewPort> CREATOR = new Parcelable.Creator<GoogleViewPort>() {

		@Override
		public GoogleViewPort createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new GoogleViewPort(in);
		}

		@Override
		public GoogleViewPort[] newArray(int size) {
			// TODO Auto-generated method stub
			return new GoogleViewPort[size];
		}
	};
	
}
