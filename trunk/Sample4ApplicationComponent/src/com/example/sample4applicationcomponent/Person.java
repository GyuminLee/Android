package com.example.sample4applicationcomponent;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
	String name;
	int age;
	int height;
	public Person() {
		
	}
	public Person(Parcel source) {
		name = source.readString();
		age = source.readInt();
		height = source.readInt();
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeInt(age);
		dest.writeInt(height);
	}
	
	public static Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

		@Override
		public Person createFromParcel(Parcel source) {
			return new Person(source);
		}

		@Override
		public Person[] newArray(int size) {
			return new Person[size];
		}
	};
}
