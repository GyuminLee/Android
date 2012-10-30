package org.tacademy.network.rss.chat;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.os.Parcel;
import android.os.Parcelable;

public class FriendItem implements Parcelable, SaxParserHandler {
	public int id;
	public String name;
	public String email;
	public String userImageUrl;
	public int type;
	
	public static final int ITEM_TYPE_FRIEND = 1;
	public static final int ITEM_TYPE_REQUEST = 2;
	public static final int ITEM_TYPE_RESPONSE = 3;

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
		} else if (tagName.equalsIgnoreCase("name")) {
			name = (String)content;
		} else if (tagName.equalsIgnoreCase("email")) {
			email = (String)content;
		} else if (tagName.equalsIgnoreCase("userImageUrl")) {
			userImageUrl = (String)content;
		} else if (tagName.equalsIgnoreCase("type")) {
			type = Integer.parseInt((String)content);
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

	public FriendItem(Parcel in) {
		// TODO Auto-generated constructor stub
		id = in.readInt();
		name = in.readString();
		email = in.readString();
		userImageUrl = in.readString();
		type = in.readInt();
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeInt(id);
		out.writeString(name);
		out.writeString(email);
		out.writeString(userImageUrl);
		out.writeInt(type);
	}
	
	public static Parcelable.Creator<FriendItem> CREATOR =
			new Parcelable.Creator<FriendItem>() {

				@Override
				public FriendItem createFromParcel(Parcel in) {
					// TODO Auto-generated method stub
					return new FriendItem(in);
				}

				@Override
				public FriendItem[] newArray(int size) {
					// TODO Auto-generated method stub
					return new FriendItem[size];
				}
			};
}
