package com.begentgroup.xmlparser;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

import org.xml.sax.Attributes;

class DataCollector {
	public static HashMap<Class,ClassInfoTable> sClassInfo = new HashMap<Class, ClassInfoTable>();
	public static Object sNullObject = new Object();
	
	Stack<DataUnit> mStack = new Stack<DataUnit>();
	
	public DataCollector(String elementName,Object object,Class clazz, int level) {
		pushDataUnit(elementName, clazz, object, level);
	}
	
	public void startElement(String tag, Attributes attributes, int level) {
		if (mStack.size() == 0) return;
		DataUnit unit = mStack.peek();
		if (unit.level + 1 == level) {
			try {
				FieldInfo fi = unit.tables.fieldInfos.get(tag);
				if (fi == null) {
					return;
				}
				Field f = fi.f;
				f.setAccessible(true);
				switch(fi.fieldType) {
				case Utils.CLASS_PRIMITIVE :
					break;
				case Utils.CLASS_ARRAY :
					Class childClass = fi.component;
					if (fi.componentType == Utils.CLASS_OBJECT) {
						Object obj = childClass.newInstance();
						ArrayData ad = unit.arrayData.get(f.getName());
						ad.array.add(obj);
						pushDataUnit(tag, childClass, obj, level);
					}
					break;
				case Utils.CLASS_COLLECTION :
					Class cc = fi.component;
					if (fi.componentType == Utils.CLASS_OBJECT) {
						Object obj = cc.newInstance();
						Collection collection = (Collection)f.get(unit.mObject);
						collection.add(obj);
						pushDataUnit(tag, cc, obj, level);
					}
					break;
				case Utils.CLASS_OBJECT :
					Object value = f.getType().newInstance();
					f.set(unit.mObject, value);
					pushDataUnit(tag, f.getType(), value , level);
					break;
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}			
		}
		
		unit = mStack.peek();
		if (unit.level == level) {
			for (int i = 0 ; i < attributes.getLength(); i++) {
				String qName = attributes.getQName(i);

				FieldInfo fi = unit.tables.fieldInfos.get(qName);
				if (fi == null) {
					return;
				}
				Field f = fi.f;
				f.setAccessible(true);
				setPrimitiveValue(unit.mObject, f,attributes.getValue(i));
			}
		}
	}
	
	public void endElement(String tag, String content, int level) {
		if (mStack.size() == 0) return;
		DataUnit unit = mStack.peek();
		if (unit.level == level && unit.elementName.equals(tag)) {
			HashMap<String,ArrayData> arrayData = unit.arrayData;
			Set<String> keys = arrayData.keySet();
			for (String key : keys) {
				ArrayData ad = arrayData.get(key);
				Field f = ad.field;
				Class cc = f.getType().getComponentType();
				ArrayList al = ad.array;
				Object values = Array.newInstance(cc, al.size());
				for (int i = 0; i < al.size(); i++) {
					setArrayValue(values, i, al.get(i),cc );
				}
				try {
					f.set(unit.mObject, values);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			mStack.pop();
		} else if (unit.level + 1 == level) {
			try {
				
				FieldInfo fi = unit.tables.fieldInfos.get(tag);
				if (fi == null) {
					return;
				}
				Field f = fi.f;
				int type = fi.fieldType;
				
				switch(type) {
				case Utils.CLASS_PRIMITIVE :
					setPrimitiveValue(unit.mObject, f, content);
					break;
				case Utils.CLASS_ARRAY :
					if (fi.componentType == Utils.CLASS_PRIMITIVE) {
						ArrayList al = unit.arrayData.get(f.getName()).array;
						setCollectionValue(al, fi.component , content);
					}
					break;
				case Utils.CLASS_COLLECTION :
					if (fi.componentType == Utils.CLASS_PRIMITIVE) {
						Collection collection = (Collection)f.get(unit.mObject);
						setCollectionValue(collection, fi.component, content);
					}
					break;
				case Utils.CLASS_OBJECT :
					break;
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	public void pushDataUnit(String elementName,Class clazz, Object object, int level) {
		DataUnit childUnit = new DataUnit();
		childUnit.elementName = elementName;
		childUnit.clazz = clazz;
		childUnit.mObject = object;
		childUnit.level = level;
		ClassInfoTable table = sClassInfo.get(clazz);
		if (table == null) {
			table = Utils.makeClassInfoTable(clazz);
			sClassInfo.put(clazz, table);
		}
		childUnit.tables = table;
		
		if (object.getClass() == clazz && Utils.getClassType(clazz) == Utils.CLASS_OBJECT) {
			
			Field[] fields = table.fields;
						
			for (Field field : fields) {
				FieldInfo fi = table.fieldInfos.get(field.getName());
				int type = fi.fieldType;
				
				if (type == Utils.CLASS_COLLECTION) {
					Object value;
					try {
						value = field.getType().newInstance();
						field.set(object, value);
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else if (type == Utils.CLASS_ARRAY) {
					ArrayData ad = new ArrayData();
					ad.field = field;
					childUnit.arrayData.put(field.getName(), ad);
				}
			}
		}
		mStack.push(childUnit);
	}
	
	public boolean setPrimitiveValue(Object obj, Field f, String value) {
		
		if (value == null || value.equals("")) return false;
		
		try {
			if (f.getType() == int.class) {
				f.setInt(obj, Integer.parseInt(value));
			} else if (f.getType() == Integer.class) {
				f.set(obj, Integer.parseInt(value));
			} else if (f.getType() == boolean.class) {
				f.setBoolean(obj, Boolean.parseBoolean(value));
			} else if (f.getType() == Boolean.class) {
				f.set(obj, Boolean.parseBoolean(value));
			} else if (f.getType() == byte.class) {
				f.setByte(obj, Byte.parseByte(value));				
			} else if (f.getType() == Byte.class) {
				f.set(obj, Byte.parseByte(value));				
			} else if (f.getType() == char.class) {
				f.setChar(obj, value.charAt(0));				
			} else if (f.getType() == Character.class) {
				f.set(obj, (Character)value.charAt(0));				
			} else if (f.getType() == double.class) {
				f.setDouble(obj, Double.parseDouble(value));				
			} else if (f.getType() == Double.class) {
				f.set(obj, Double.parseDouble(value));				
			} else if (f.getType() == float.class) {
				f.setFloat(obj, Float.parseFloat(value));
			} else if (f.getType() == Float.class) {
				f.set(obj, Float.parseFloat(value));
			} else if (f.getType() == long.class) {
				f.setLong(obj, Long.parseLong(value));
			} else if (f.getType() == Long.class) {
				f.set(obj, Long.parseLong(value));
			} else if (f.getType() == short.class) {
				f.setShort(obj, Short.parseShort(value));				
			} else if (f.getType() == Short.class) {
				f.set(obj, Short.parseShort(value));				
			} else if (f.getType() == String.class) {
				f.set(obj, value);				
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	private boolean setCollectionValue(Collection collection, Class gc,
			String value) {
		if (value == null || value.equals("")) return false;
		
		try {
			if (gc == int.class || gc == Integer.class) {
				collection.add( Integer.parseInt(value));
			} else if (gc == boolean.class || gc == Boolean.class) {
				collection.add( Boolean.parseBoolean(value));
			} else if (gc == byte.class || gc == Byte.class) {
				collection.add( Byte.parseByte(value));				
			} else if (gc == char.class || gc == Character.class) {
				collection.add( value.charAt(0));				
			} else if (gc == double.class || gc == Double.class) {
				collection.add( Double.parseDouble(value));				
			} else if (gc == float.class || gc == Float.class) {
				collection.add( Float.parseFloat(value));
			} else if (gc == long.class || gc == Long.class) {
				collection.add( Long.parseLong(value));
			} else if (gc == short.class || gc == Short.class) {
				collection.add( Short.parseShort(value));				
			} else if (gc == String.class) {
				collection.add(value);				
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return true;
	}

	private void setArrayValue(Object array, int index, Object value, Class cc) {
		if (cc == int.class) {
			Array.setInt(array,index,(Integer)value);
		} else if (cc == boolean.class) {
			Array.setBoolean(array, index, (Boolean)value);
		} else if (cc == byte.class) {
			Array.setChar(array, index, (Character)value);
		} else if (cc == double.class) {
			Array.setDouble(array, index, (Double)value);
		} else if (cc == float.class) {
			Array.setFloat(array, index, (Float)value);
		} else if (cc == long.class) {
			Array.setLong(array, index, (Long)value);
		} else if (cc == short.class) {
			Array.setShort(array, index, (Short)value);
		} else {
			Array.set(array, index, value);
		}
	}

	
}
