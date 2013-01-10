package com.example.testlaunchbrowsersample;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {

	String name;
	int age;
	
	public Profile(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public Profile(Parcel source) {
		// TODO Auto-generated constructor stub
		name = source.readString();
		age = source.readInt();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(name);
		dest.writeInt(age);
	}
	
	public static Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {

		@Override
		public Profile createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Profile(source);
		}

		@Override
		public Profile[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Profile[size];
		}
	};
}
