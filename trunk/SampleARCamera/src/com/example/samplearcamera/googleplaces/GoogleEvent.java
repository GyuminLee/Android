package com.example.samplearcamera.googleplaces;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.samplearcamera.parser.SaxParserHandler;
import com.example.samplearcamera.parser.SaxResultParser;

public class GoogleEvent implements Parcelable, SaxParserHandler {

	public int duration;
	public String event_id;
	public int start_time;
	public String summary;
	public String url;
	private String tag = "event";
	
	public GoogleEvent() {
		
	}
	
	public GoogleEvent(String tag) {
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
		if (tagName.equalsIgnoreCase("duration")) {
			duration = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("event_id")) {
			event_id = (String)content;
		} else if (tagName.equalsIgnoreCase("start_time")) {
			start_time = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("summary")) {
			summary = (String)content;
		} else if (tagName.equalsIgnoreCase("url")) {
			url = (String)content;
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

	private GoogleEvent(Parcel in) {
		duration = in.readInt();
		event_id = in.readString();
		start_time = in.readInt();
		summary = in.readString();
		url = in.readString();
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeInt(duration);
		out.writeString(event_id);
		out.writeInt(start_time);
		out.writeString(summary);
		out.writeString(url);
	}
	
	public static Parcelable.Creator<GoogleEvent> CREATOR = new Parcelable.Creator<GoogleEvent>() {

		@Override
		public GoogleEvent createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new GoogleEvent(source);
		}

		@Override
		public GoogleEvent[] newArray(int size) {
			// TODO Auto-generated method stub
			return new GoogleEvent[size];
		}
	};
}
