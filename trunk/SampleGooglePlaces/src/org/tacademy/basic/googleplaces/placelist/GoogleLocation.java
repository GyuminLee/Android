package org.tacademy.basic.googleplaces.placelist;

import org.tacademy.basic.googleplaces.parser.SaxParserHandler;
import org.tacademy.basic.googleplaces.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.os.Parcel;
import android.os.Parcelable;

public class GoogleLocation implements Parcelable, SaxParserHandler {
	public double lat;
	public double lng;
	private String tag = "location";
	
	public GoogleLocation() {
		
	}
	
	public GoogleLocation(String tag) {
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
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeDouble(lat);
		out.writeDouble(lng);
	}
	
	private GoogleLocation(Parcel in) {
		lat = in.readDouble();
		lng = in.readDouble();
	}
	
	public static Parcelable.Creator<GoogleLocation> CREATOR = new Parcelable.Creator<GoogleLocation>() {

		@Override
		public GoogleLocation createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new GoogleLocation(source);
		}

		@Override
		public GoogleLocation[] newArray(int size) {
			// TODO Auto-generated method stub
			return new GoogleLocation[size];
		}
	};
	
}
