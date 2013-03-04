package com.example.hellonetwork;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.hellonetwork.parser.SaxParserHandler;
import com.example.hellonetwork.parser.SaxResultParser;

public class NaverMovieItem implements SaxParserHandler, Parcelable {

	public String title;
	public String link;
	public String image;


	public NaverMovieItem() {
		
	}

	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "item";
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
	public void writeToParcel(Parcel dest, int flag) {
		// TODO Auto-generated method stub
		dest.writeString(title);
		dest.writeString(link);
		dest.writeString(image);
		
	}

	public NaverMovieItem(Parcel source) {
		// TODO Auto-generated constructor stub
		title = source.readString();
		link = source.readString();
		image = source.readString();
	}
	
	public static Parcelable.Creator<NaverMovieItem> CREATOR = new Parcelable.Creator<NaverMovieItem>() {

		@Override
		public NaverMovieItem createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new NaverMovieItem(source);
		}

		@Override
		public NaverMovieItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new NaverMovieItem[size];
		}
	};

}
