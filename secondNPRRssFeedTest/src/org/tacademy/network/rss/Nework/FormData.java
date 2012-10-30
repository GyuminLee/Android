package org.tacademy.network.rss.Nework;

public class FormData {
	public static final int FORM_DATA_TYPE_NORMAL = 1;
	public static final int FORM_DATA_TYPE_FILE = 2;
	public int type;
	public String key;
	public String value;
	public String contentType;
	
	public FormData() {
		
	}
	
	public FormData(String key,String value) {
		this.type = FORM_DATA_TYPE_NORMAL;
		this.key = key;
		this.value = value;
	}
	
	public FormData(String key,String filePath,String contentType) {
		this.type = FORM_DATA_TYPE_FILE;
		this.key = key;
		this.value = filePath;
		this.contentType = contentType;
	}

}
