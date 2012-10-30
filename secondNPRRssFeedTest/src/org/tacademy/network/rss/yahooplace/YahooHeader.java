package org.tacademy.network.rss.yahooplace;

import android.os.Parcel;
import android.os.Parcelable;

public class YahooHeader implements Parcelable {
	public String publisher;
	public int error;
	public String errorMessage;
	public int found;
	public int describeContents() {
		return 0;
	}
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(publisher);
		dest.writeInt(error);
		dest.writeString(errorMessage);
		dest.writeInt(found);
	}
	
	public YahooHeader() {
		
	}
	
	public YahooHeader(Parcel source) {
		publisher = source.readString();
		error = source.readInt();
		errorMessage = source.readString();
		found = source.readInt();
	}

	public static Parcelable.Creator<YahooHeader> CREATOR =
		new Parcelable.Creator<YahooHeader>() {

			public YahooHeader createFromParcel(Parcel source) {
				return new YahooHeader(source);
			}

			public YahooHeader[] newArray(int size) {
				return new YahooHeader[size];
			}
		};
}
