package org.tacademy.basic.samplelist2;

import android.os.Parcel;
import android.os.Parcelable;

public class MyData implements Parcelable{
	int imageResId;
	String text;
	public MyData(int resId,String text) {
		this.imageResId = resId;
		this.text = text;
	}
	public MyData(Parcel source) {
		// TODO Auto-generated constructor stub
		imageResId = source.readInt();
		text = source.readString();
	}
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(imageResId);
		dest.writeString(text);
	}
	
	public static Parcelable.Creator<MyData> CREATOR = new Parcelable.Creator<MyData>() {

		public MyData createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new MyData(source);
		}

		public MyData[] newArray(int size) {
			// TODO Auto-generated method stub
			return new MyData[size];
		}
	};
}
