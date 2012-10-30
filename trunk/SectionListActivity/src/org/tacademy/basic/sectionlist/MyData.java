package org.tacademy.basic.sectionlist;

public class MyData implements Comparable<MyData> {
	public int id;
	public int sectionId;
	public String sectionText;
	public String childText;
	
	public MyData(int sectionId, String sectionText, String childText) {
		this.sectionId = sectionId;
		this.sectionText = sectionText;
		this.childText = childText;
	}

	public int compareTo(MyData mydata) {
		return (sectionId - mydata.sectionId);
	}
}
