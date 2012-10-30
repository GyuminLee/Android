package org.tacademy.network.rss.c2dm;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable, SaxParserHandler {
	public int id;
	public String email;
	public String name;
	public String imageUrl;
	public User() {
		
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
		if (tagName.equalsIgnoreCase("id")) {
			id = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("email")) {
			email = (String)content;
		} else if (tagName.equalsIgnoreCase("name")) {
			name = (String)content;
		} else if (tagName.equalsIgnoreCase("imageUrl")) {
			imageUrl = (String)content;
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
		out.writeInt(id);
		out.writeString(email);
		out.writeString(name);
		out.writeString(imageUrl);
	}

	public User(Parcel in) {
		// TODO Auto-generated constructor stub
		id = in.readInt();
		email = in.readString();
		name = in.readString();
		imageUrl = in.readString();
	}
	
	public static Parcelable.Creator<User> CREATOR =
			new Parcelable.Creator<User>() {

				@Override
				public User createFromParcel(Parcel in) {
					// TODO Auto-generated method stub
					return new User(in);
				}

				@Override
				public User[] newArray(int size) {
					// TODO Auto-generated method stub
					return new User[size];
				}
			};
}
