package org.tacademy.basic.samplewebservice;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class ChangeLengthUnit implements KvmSerializable {

	String mLengthValue;
	String mFromUnit;
	String mToUnit;

	private final static String PARAM_LENGTH_VALUE = "LengthValue";
	private final static String PARAM_FROM_UNIT = "fromLengthUnit";
	private final static String PARAM_TO_UNIT = "toLengthUnit";
	
	public ChangeLengthUnit(double lengthValue,String fromUnit,String toUnit) {
		mLengthValue = "" + lengthValue;
		mFromUnit = fromUnit;
		mToUnit = toUnit;
	}
	
	@Override
	public Object getProperty(int index) {
		// TODO Auto-generated method stub
		switch(index) {
			case 0 :
				return mLengthValue;
			case 1 :
				return mFromUnit;
			case 2 :
				return mToUnit;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable hash, PropertyInfo info) {
		// TODO Auto-generated method stub
		switch (index) {
			case 0 :
				info.type = double.class;
				info.name = PARAM_LENGTH_VALUE;
				break;
			case 1 :
				info.type = PropertyInfo.STRING_CLASS;
				info.name = PARAM_FROM_UNIT;
				break;
			case 2 :
				info.type = PropertyInfo.STRING_CLASS;
				info.name = PARAM_TO_UNIT;
				break;
			default :
				break;
		}
	}

	@Override
	public void setProperty(int index, Object value) {
		// TODO Auto-generated method stub
		switch(index) {
			case 0 :
				mLengthValue = value.toString();
				break;
			case 1 :
				mFromUnit = value.toString();
				break;
			case 2 :
				mToUnit = value.toString();
				break;
			default :
				break;
		}
	}

}
