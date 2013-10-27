package com.begentgroup.xmlparser;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

class Utils {

	public static final int CLASS_NOT_FIXED = -1;
	public static final int CLASS_PRIMITIVE = 0;
	public static final int CLASS_ARRAY = 1;
	public static final int CLASS_COLLECTION = 2;
	public static final int CLASS_OBJECT = 3;

	public static int getClassType(Class clazz) {
		if (clazz.isPrimitive() || 
			clazz == Integer.class || clazz == Long.class || clazz == Short.class ||
			clazz == String.class || 
			clazz == Character.class || clazz == Boolean.class ||
			clazz == Float.class || clazz == Double.class) {
			return CLASS_PRIMITIVE;
		} else if (clazz.isArray()) {
			return CLASS_ARRAY;
		} else if (Collection.class.isAssignableFrom(clazz)) {
			return CLASS_COLLECTION;
		} 
		return CLASS_OBJECT;
	}
	
	public static ClassInfoTable makeClassInfoTable(Class<? extends Object> clazz) {
		ClassInfoTable table = new ClassInfoTable();
		table.fields = clazz.getDeclaredFields();
		for (Field field : table.fields) {
			FieldInfo info = new FieldInfo();
			info.f = field;
			info.fieldType = Utils.getClassType(field.getType());
			if (field.getAnnotation(Exclusion.class) != null) {
				info.annotationType = FieldInfo.ANNOTATION_TYPE_EXCLUSION;
			} else if (field.getAnnotation(Attribute.class) != null) {
				info.annotationType = FieldInfo.ANNOTATION_TYPE_ATTRIBUTE;
			} else if (field.getAnnotation(Element.class) != null) {
				info.annotationType = FieldInfo.ANNOTATION_TYPE_ELEMENT;
			}
				
			switch (info.fieldType) {
			case Utils.CLASS_ARRAY :
				info.component = field.getType().getComponentType();
				info.componentType = Utils.getClassType(info.component);
				break;
			case Utils.CLASS_COLLECTION :
				info.component = (Class)((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
				info.componentType = Utils.getClassType(info.component);
				break;
			}
			table.fieldInfos.put(field.getName(), info);
		}
		return table;
	}
	
}
