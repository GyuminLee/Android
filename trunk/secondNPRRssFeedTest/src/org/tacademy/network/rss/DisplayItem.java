package org.tacademy.network.rss;

import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.navernews.NaverSearchNewsRequest;
import org.tacademy.network.rss.npr.RssRequest;

import android.os.Parcel;
import android.os.Parcelable;

public class DisplayItem implements Parcelable {
	public String caption;
	public String url;
	public int type;
	public final static int TYPE_NPR_NEWS = 1;
	public final static int TYPE_NAVER_SEARCH_NEWS = 2;
	public final static int TYPE_NAVER_SEARCH_MOVIE = 3;
	
	public DisplayItem(String caption,String url,int type) {
		this.caption = caption;
		this.url = url;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return caption;
	}

	public NetworkRequest getRequest() {
		switch(type) {
			case TYPE_NPR_NEWS :
				return new RssRequest(url);
			case TYPE_NAVER_SEARCH_NEWS :
				return new NaverSearchNewsRequest(url);
			case TYPE_NAVER_SEARCH_MOVIE :
				break;
		}
		return null;
	}
	
	public int describeContents() {
		return 0;
	}
	
	public void writeToParcel(Parcel out, int flag) {
		out.writeString(caption);
		out.writeString(url);
		out.writeInt(type);
	}
	
	public static final Parcelable.Creator<DisplayItem> CREATOR = 
		new Parcelable.Creator<DisplayItem>() {

			public DisplayItem createFromParcel(Parcel in) {
				return new DisplayItem(in);
			}

			public DisplayItem[] newArray(int size) {
				return new DisplayItem[size];
			}
		};
	
	private DisplayItem(Parcel in) {
		caption = in.readString();
		url = in.readString();
		type = in.readInt();
	}
}
