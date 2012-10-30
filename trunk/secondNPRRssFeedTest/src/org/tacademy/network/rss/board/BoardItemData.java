package org.tacademy.network.rss.board;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.os.Parcel;
import android.os.Parcelable;

public class BoardItemData implements Parcelable, SaxParserHandler {

	public int id;
	public String imageUrl;
	public String title;
	public String content;
	public int userid;
	public String userName;
	public String userImageUrl;
	public int replyCount;
	

	public int describeContents() {
		return 0;
	}
	
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(id);
		out.writeString(imageUrl);
		out.writeString(title);
		out.writeString(content);
		out.writeInt(userid);
		out.writeString(userName);
		out.writeString(userImageUrl);
		out.writeInt(replyCount);
	}
	
	public BoardItemData(Parcel in) {
		id = in.readInt();
		imageUrl = in.readString();
		title = in.readString();
		content = in.readString();
		userid = in.readInt();
		userName = in.readString();
		userImageUrl = in.readString();
		replyCount = in.readInt();
	}

	public BoardItemData() {
		id = -1;
	}

	public static Parcelable.Creator<BoardItemData> CREATOR =
		new Parcelable.Creator<BoardItemData>() {

			public BoardItemData createFromParcel(Parcel source) {
				return new BoardItemData(source);
			}

			public BoardItemData[] newArray(int size) {
				return new BoardItemData[size];
			}
		};

	public String getTagName() {
		return "item";
	}

	public void parseStartElement(String tagName, Attributes attributes, String namespaceUri, String qualifiedName, SaxResultParser parser) throws SAXException {
	}

	public void parseEndElement(String tagName, Object content, String namespaceUri, String qualifiedName, SaxResultParser parser) throws SAXException {
		try {
			if (tagName.equalsIgnoreCase("id")) {
				this.id = Integer.parseInt((String)content);
			} else if (tagName.equalsIgnoreCase("title")) {
					this.title = URLDecoder.decode((String)content, "UTF-8");
			} else if (tagName.equalsIgnoreCase("content")) {
				this.content = URLDecoder.decode((String)content, "UTF-8");
			} else if (tagName.equalsIgnoreCase("imageUrl")) {
				this.imageUrl = (String)content;
			} else if (tagName.equalsIgnoreCase("userid")) {
				this.userid = Integer.parseInt((String)content);
			} else if (tagName.equalsIgnoreCase("username")) {
				this.userName = URLDecoder.decode((String)content, "UTF-8");
			} else if (tagName.equalsIgnoreCase("userimageurl")) {
				this.userImageUrl = (String)content;
			} else if (tagName.equalsIgnoreCase("replycount")) {
				this.replyCount = Integer.parseInt((String)content);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object getParseResult() {
		return this;
	}
}
