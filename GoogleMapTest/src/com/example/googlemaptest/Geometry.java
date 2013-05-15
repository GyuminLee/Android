package com.example.googlemaptest;

import android.os.Parcel;
import android.os.Parcelable;

public class Geometry implements Parcelable {

	Location location;
	
	public Geometry() {
		
	}

	public Geometry(Parcel source) {
		// TODO Auto-generated constructor stub
		location = source.readParcelable(Location.class.getClassLoader());
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeParcelable(location, flags);
	}
	
	public static Creator<Geometry> CREATOR = new Creator<Geometry>() {

		@Override
		public Geometry createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Geometry(source);
		}

		@Override
		public Geometry[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Geometry[size];
		}
	};
}
