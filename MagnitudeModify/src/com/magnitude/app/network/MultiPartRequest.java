package com.magnitude.app.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import android.os.Environment;

public class MultiPartRequest extends PostRequest {

	private String mBoundary = makeDelimeter();

	private final static String CRLF = "\r\n";
	private final static String TWO_HYPHONES = "--";
	private final static String CONTENT_DISPOSITION_FORMAT = "Content-Disposition: form-data; name=\"%s\""+CRLF;
	private final static String CONTENT_DISPOSITION_FORMAT_UPLOAD = "Content-Disposition: form-data; name=\"%s\"; filename=\"%s\""+CRLF;
	
	private final static String CONTENT_DISPOSITION_FORMAT_CONTENT_TYPE = "Content-Type: %s" + CRLF;
	private final static String CONTENT_DISPOSITION_FORMAT_CONTENT_TYPE_TEXT = "Content-Type: %s; charset=%s"+CRLF;
	private final static String CONTENT_DISPOSITION_FORMAT_TRANSFOR_ENCODING = "Content-Transfer-Encoding: binary" + CRLF;
	private final static int MAX_BUFFER_SIZE = 1024;
	
	public void addFormData(String key,String filePath,String contentType) {
		addFormData(new FormData(key,filePath,contentType));
	}

	@Override
	public boolean setHeader(HttpURLConnection conn) {
		conn.setRequestProperty("Accept-Charset", mCharset);
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + mBoundary);
		return true;
	}

	@Override
	public boolean setOutput(HttpURLConnection conn) throws IOException {
		OutputStream output = conn.getOutputStream();		
		
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(output,mCharset),true);

		for(FormData formdata : formdatas) {
			if (formdata.type == FormData.FORM_DATA_TYPE_NORMAL) {
				writeField(output,formdata.key,formdata.value);
			} else if (formdata.type == FormData.FORM_DATA_TYPE_FILE) {
				writeFile(output,formdata.key,formdata.contentType,new File(formdata.value));
			}
		}
		
		writer.append(TWO_HYPHONES + mBoundary + TWO_HYPHONES);
		writer.close();
		return true;
	}
	

	private String makeDelimeter() {
		//return "----WebKitFormBoundaryoXdMuJLSlA5W3IAU";
		return "----WebKitFormBoundary"+Long.toHexString(System.currentTimeMillis());
	}

	private void writeField(OutputStream output,String fieldName,String value) throws IOException {
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(output,mCharset),true);
		writer.append(TWO_HYPHONES + mBoundary + CRLF);
		writer.append(String.format(CONTENT_DISPOSITION_FORMAT,fieldName));
		//writer.append(String.format(CONTENT_DISPOSITION_FORMAT_CONTENT_TYPE_TEXT, "text/plain", mCharset));
		writer.append(CRLF);
		//writer.append(URLEncoder.encode(value, mCharset));
		writer.append(value);
		writer.append(CRLF);
		writer.flush();
	}
	
	private void writeFile(OutputStream output,String fieldName,String contentType, File file) throws IOException {
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(output,mCharset),true);

		boolean isText = false;
		if (contentType.startsWith("text")) {
			isText = true;
		}
		writer.append(TWO_HYPHONES + mBoundary + CRLF);
		String fileName = file.getName();
		writer.append(String.format(CONTENT_DISPOSITION_FORMAT_UPLOAD,fieldName,fileName));
		if (isText) {
			writer.append(String.format(CONTENT_DISPOSITION_FORMAT_CONTENT_TYPE_TEXT, contentType,mCharset));
		} else {
			writer.append(String.format(CONTENT_DISPOSITION_FORMAT_CONTENT_TYPE, contentType));
			//writer.append(CONTENT_DISPOSITION_FORMAT_TRANSFOR_ENCODING);
		}
		writer.append(CRLF);
		writer.flush();
		
		if (isText) {
		    BufferedReader reader = null;
		    try {
		        reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), mCharset));
		        for (String line; (line = reader.readLine()) != null;) {
		            writer.append(line).append(CRLF);
		        }
		    } finally {
		        if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
		    }
		} else {
		    InputStream input = null;
		    try {
		        input = new FileInputStream(file);
		        byte[] buffer = new byte[MAX_BUFFER_SIZE];
		        for (int length = 0; (length = input.read(buffer)) > 0;) {
		            output.write(buffer, 0, length);
		        }
		        output.flush(); // Important! Output cannot be closed. Close of writer will close output as well.
		    } finally {
		        if (input != null) try { input.close(); } catch (IOException logOrIgnore) {}
		    }
			writer.append(CRLF);
			
		}
		
		writer.flush();
		
	}
	
}
