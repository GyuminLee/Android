package com.example.sample4database.manager;

import android.provider.BaseColumns;

public class DBConstant {
	public interface PersonTable extends BaseColumns {
		public static final String TABLE_NAME = "persontbl";
		public static final String FIELD_NAME = "name";
		public static final String FIELD_AGE = "age";
	}
	
	public interface HouseTable extends BaseColumns {
		public static final String TABL_NAME = "housetbl";
		public static final String FIELD_ADDRESS = "address";
		public static final String FIELD_AREA = "area";
	}
}
