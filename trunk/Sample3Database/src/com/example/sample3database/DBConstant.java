package com.example.sample3database;

import android.provider.BaseColumns;

public class DBConstant {

	public static class PersonTable implements BaseColumns {
		public static final String TABLE_NAME = "persontbl";
		public static final String NAME = "name";
		public static final String AGE = "age";
	}
}
