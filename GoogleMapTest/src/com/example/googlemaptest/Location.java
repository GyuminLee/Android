package com.example.googlemaptest;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {
	double lat;
	double lng;
	
	public Location() {
		
	}
	
	public Location(Parcel source) {
		// TODO Auto-generated constructor stub
		lat = source.readDouble();
		lng = source.readDouble();
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeDouble(lat);
		dest.writeDouble(lng);
	}
	
	public static Creator<Location> CREATOR = new Creator<Location>() {

		@Override
		public Location createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Location(source);
		}

		@Override
		public Location[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Location[size];
		}
	};
}
