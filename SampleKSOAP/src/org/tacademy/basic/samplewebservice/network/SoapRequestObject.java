package org.tacademy.basic.samplewebservice.network;

import java.util.ArrayList;

public class SoapRequestObject {
	public ArrayList<SoapRequestValue> values = new ArrayList<SoapRequestValue>();
	public void add(String key,Object object) {
		values.add(new SoapRequestValue(key,object));
	}
}
