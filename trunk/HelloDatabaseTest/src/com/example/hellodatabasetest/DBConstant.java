package com.example.hellodatabasetest;

import android.provider.BaseColumns;

public class DBConstant {
	public static class TableMessage implements BaseColumns {
		public static final String TABLE_NAME = "tblMessage";
		public static final String FIELD_MESSAGE = "message";
		public static final String FIELD_TYPE = "type";
		public static final String FIELD_DATE = "indate";
	}
}
