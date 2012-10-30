package org.tacademy.basic.googleplaces.placelist;

import org.tacademy.basic.googleplaces.parser.SaxParserHandler;
import org.tacademy.basic.googleplaces.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class Geometry implements Parcelable, SaxParserHandler {
	public GoogleLocation location;
	public GoogleViewPort viewport;
	
	private String tag = "geometry";
	
	public Geometry() {
		
	}
	
	public Geometry(String tag) {
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
		if (tagName.equalsIgnoreCase("location")) {
			GoogleLocation location = new GoogleLocation();
			parser.pushHandler(location);
		} else if (tagName.equalsIgnoreCase("viewport")) {
			GoogleViewPort viewport = new GoogleViewPort();
			parser.pushHandler(viewport);
		}
	}
	
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("location")) {
			this.location = (GoogleLocation)content;
		} else if (tagName.equalsIgnoreCase("viewport")) {
			this.viewport = (GoogleViewPort)content;
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
		out.writeParcelable(location, flags);
		out.writeParcelable(viewport, flags);
	}
	
	private Geometry(Parcel in) {
		location = in.readParcelable(Location.class.getClassLoader());
		viewport = in.readParcelable(GoogleViewPort.class.getClassLoader());
	}
	
	public static Parcelable.Creator<Geometry> CREATOR = new Parcelable.Creator<Geometry>() {

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
}
