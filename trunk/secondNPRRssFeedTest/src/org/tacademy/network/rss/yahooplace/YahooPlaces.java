package org.tacademy.network.rss.yahooplace;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class YahooPlaces implements Parcelable {
	YahooHeader header;
	ArrayList<YahooPlacesItem> items = new ArrayList<YahooPlacesItem>();
	public int describeContents() {
		return 0;
	}
	
	public YahooPlaces() {
		
	}
	
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(header, flags);
		dest.writeTypedList(items);
	}

	public YahooPlaces(Parcel source) {
		header = source.readParcelable(YahooHeader.class.getClassLoader());
		source.readTypedList(items, YahooPlacesItem.CREATOR);
	}

	
	public static Parcelable.Creator<YahooPlaces> CREATOR = 
		new Parcelable.Creator<YahooPlaces>() {

			public YahooPlaces createFromParcel(Parcel source) {
				return new YahooPlaces(source);
			}

			public YahooPlaces[] newArray(int size) {
				return new YahooPlaces[size];
			}
		};
}
