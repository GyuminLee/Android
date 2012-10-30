package org.tacademy.network.rss.yahooplace;

import android.os.Parcel;
import android.os.Parcelable;

public class YahooPlacesItem implements Parcelable {
	public int num;
	public double latitude;
	public double longitude;
	public String name;
	public String street;
	public String city;
	public String county;
	public String state;
	public String country;
	
	public YahooPlacesItem() {
		
	}
	
	public int describeContents() {
		return 0;
	}
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(num);
		out.writeDouble(latitude);
		out.writeDouble(longitude);
		out.writeString(name);
		out.writeString(street);
		out.writeString(city);
		out.writeString(county);
		out.writeString(state);
		out.writeString(country);
	}

	private YahooPlacesItem(Parcel in) {
		num = in.readInt();
		latitude = in.readDouble();
		longitude = in.readDouble();
		name = in.readString();
		street = in.readString();
		city = in.readString();
		county = in.readString();
		state = in.readString();
		country = in.readString();
	}
	
	public static final Parcelable.Creator<YahooPlacesItem> CREATOR = 
		new Parcelable.Creator<YahooPlacesItem>() {

			public YahooPlacesItem createFromParcel(Parcel source) {
				return new YahooPlacesItem(source);
			}

			public YahooPlacesItem[] newArray(int size) {
				return new YahooPlacesItem[size];
			}
		};
		
}
