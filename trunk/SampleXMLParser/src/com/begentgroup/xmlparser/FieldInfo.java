package com.begentgroup.xmlparser;

import java.lang.reflect.Field;

class FieldInfo {
	public static final int ANNOTATION_TYPE_NONE = 0;
	public static final int ANNOTATION_TYPE_ATTRIBUTE = 1;
	public static final int ANNOTATION_TYPE_ELEMENT = 2;
	public static final int ANNOTATION_TYPE_EXCLUSION = 3;
	
	Field f;
	int annotationType = ANNOTATION_TYPE_NONE;
	int fieldType = Utils.CLASS_NOT_FIXED;
	int componentType = Utils.CLASS_NOT_FIXED;
	Class component;
}
