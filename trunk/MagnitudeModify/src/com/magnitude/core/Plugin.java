package com.magnitude.core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A class representing a Plugin for Magnitude
 * @author Magnitude client
 */
public class Plugin implements Parcelable {

	private String name;
	private String id;
	private String version;
	private String description;
	private String intent;
	private String apkURL;
	private String status;
	
	public static String STATUS_INSTALLED = "installed";
	public static String STATUS_AVAILABLE = "available";
	public static String STATUS_OUTDATED = "outdated";
	public static String STATUS_NOT_RELEASED = "coming soon !";
	
	public Plugin(String name, String id,String version,String description,String intent,String apkURL,String status) {
		super();
		this.name=name;
		this.id=id;
		this.version=version;
		this.description=description;
		this.intent=intent;
		this.apkURL=apkURL;
		this.status=status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIntent() {
		return intent;
	}

	public void setIntent(String intent) {
		this.intent = intent;
	}

	public String getApkURL() {
		return apkURL;
	}

	public void setApkURL(String apkURL) {
		this.apkURL = apkURL;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(id);
		dest.writeString(version);
		dest.writeString(description);
		dest.writeString(intent);
		dest.writeString(apkURL);
		dest.writeString(status);
	}
	
	 public static final Parcelable.Creator<Plugin> CREATOR = new Parcelable.Creator<Plugin>() {
		public Plugin createFromParcel(Parcel in) {
     return new Plugin(in);
 }

		public Plugin[] newArray(int size) {
			return new Plugin[size];
		}
};

	private Plugin(Parcel in) {
	 name= in.readString();
	 id= in.readString();
	 version= in.readString();
	 description= in.readString();
	 intent= in.readString();
	 apkURL= in.readString();
	 status= in.readString();
	}

	
	
}
