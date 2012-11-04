package com.magnitude.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A Helper class for getting JSON objects from the web
 * 
 * @author Magnitude client
 * 
 */
public class JSONHelper {

	/**
	 * a URL the Helper will connect to
	 */
	private static URL myURL;
	/**
	 * The connection to the myURL field
	 */
	private static URLConnection uc;
	/**
	 * Data retrieved from uc
	 */
	private static InputStream in;
	

	/**
	 * Returns the JSONObject at the specified URL.
	 * 
	 * @param url The url where to get the json file.
	 * @return The element at the specified url.
	 */
	public static JSONObject getJSONObject(String url) {
		String res = null;
		JSONObject producedObject = null;
		initURL(url);
		connect();
		try {
			res = convertStreamToString(in);
			if (!res.equals("")) {
			producedObject = new JSONObject(res);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return producedObject;
	}

	/**
	 * Initializes url into helper class.
	 * 
	 * @param url
	 *            The url that the Helperclass will download the JSONObject
	 *            from.
	 */
	private static void initURL(String url) {
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Opens connection to url and gets data from it.
	 */
	private static boolean connect() {
		boolean ret = true;
		try {
			uc = myURL.openConnection();
			in = uc.getInputStream();
		} catch (IOException e) {
			ret = false;
		}
		return ret;
	}

	/**
	 * Converts an InputStream to a String.
	 * 
	 * @param is
	 *            The InputStream object to convert
	 * @return A String with the data from the InputStream <br> "" if Error during process.
	 */
	private static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		if (is != null) {
			StringBuilder sb = new StringBuilder();
			String line;
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "ISO_8859_1"), 16192);
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sb.toString();
		} else {
			return "";
		}
	}
}
