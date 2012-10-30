package org.tacademy.network.rss.board;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.tacademy.network.rss.parser.SaxParserHandler;
import org.tacademy.network.rss.parser.SaxResultParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.os.Parcel;
import android.os.Parcelable;

public class ReplyItemData implements Parcelable, SaxParserHandler {
	public int id;
	public int userId;
	public int boardId;
	public String content;
	public String userName;
	public String userImageUrl;
	
	public ReplyItemData() {
		id = -1;
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
		out.writeInt(userId);
		out.writeInt(boardId);
		out.writeString(content);
		out.writeString(userName);
		out.writeString(userImageUrl);
	}
	
	public ReplyItemData(Parcel in) {
		// TODO Auto-generated constructor stub
		id = in.readInt();
		userId = in.readInt();
		boardId = in.readInt();
		content = in.readString();
		userName = in.readString();
		userImageUrl = in.readString();
	}

	public static Parcelable.Creator<ReplyItemData> CREATOR =
			new Parcelable.Creator<ReplyItemData>() {

				@Override
				public ReplyItemData createFromParcel(Parcel in) {
					// TODO Auto-generated method stub
					return new ReplyItemData(in);
				}

				@Override
				public ReplyItemData[] newArray(int size) {
					// TODO Auto-generated method stub
					return new ReplyItemData[size];
				}
			};

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
		try {
			if (tagName.equalsIgnoreCase("id")) {
				this.id = Integer.parseInt((String)content);
			} else if (tagName.equalsIgnoreCase("userid")) {
				this.userId = Integer.parseInt((String)content);
			} else if (tagName.equalsIgnoreCase("boardid")) {
				this.boardId = Integer.parseInt((String)content);
			} else if (tagName.equalsIgnoreCase("content")) {
					this.content = URLDecoder.decode((String)content, "UTF-8");
			} else if (tagName.equalsIgnoreCase("username")) {
				this.userName = URLDecoder.decode((String)content, "UTF-8");
			} else if (tagName.equalsIgnoreCase("userimageurl")) {
				this.userImageUrl = (String)content;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}
	
}
