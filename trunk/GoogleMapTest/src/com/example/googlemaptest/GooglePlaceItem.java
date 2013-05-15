package com.example.googlemaptest;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class GooglePlaceItem implements Parcelable {

	Geometry geometry;
	String icon;
	String id;
	String name;
	String reference;
	ArrayList<String> types;
	String vicinity;
	
	public GooglePlaceItem() {
		
	}

	public GooglePlaceItem(Parcel source) {
		// TODO Auto-generated constructor stub
		geometry = source.readParcelable(Geometry.class.getClassLoader());
		icon = source.readString();
		id = source.readString();
		name = source.readString();
		reference = source.readString();
		types = new ArrayList<String>();
		source.readStringList(types);
		vicinity = source.readString();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeParcelable(geometry, flags);
		dest.writeString(icon);
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(reference);
		dest.writeStringList(types);
		dest.writeString(vicinity);
		
	}
	
	public static Creator<GooglePlaceItem> CREATOR = new Creator<GooglePlaceItem>() {

		@Override
		public GooglePlaceItem createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new GooglePlaceItem(source);
		}

		@Override
		public GooglePlaceItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new GooglePlaceItem[size];
		}
	};
}
