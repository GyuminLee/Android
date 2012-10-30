package org.tacademy.network.rss.upload;

public class UploadResult {
	public static final int RESULT_SUCCESS = 1;
	public static final int RESULT_FAIL = 0;
	public static final int RESULT_NOT_LOGIN = 2;
	public static final int RESULT_NOT_USER_ADD = 3;
	
	public static final String MESSAGE_SUCCESS = "Success";
	public static final String MESSAGE_FAIL = "Fail";
	public static final String MESSAGE_NOT_LOGIN = "NotLogin";
	public static final String MESSAGE_NOT_USER_ADD = "NotAdded";
	
	public int result;
	public int resultId = -1;
}
