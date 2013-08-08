package org.tacademy.basic.samplewebservice.network;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

import org.tacademy.basic.samplewebservice.parser.SoapParser;

public class SoapRequest extends NetworkRequest {
	
	protected final static String SOAP_ENV="http://www.w3.org/2003/05/soap-envelope";
	protected final static String XSI="http://www.w3.org/2001/XMLSchema-instance";
	protected final static String XSD="http://www.w3.org/2001/XMLSchema";
	protected final static String SOAP1_1="http://schemas.xmlsoap.org/soap/envelope/";
	protected final static String SOAP1_2="http://www.w3.org/2003/05/soap-envelope";
	protected final static String CONTENT_TYPE_SOAP_1_2 = "application/soap+xml;charset=utf-8";
	protected final static String CONTENT_TYPE_SOAP_1_1 = "text/xml;charset=utf-8";
	
	protected String CONTENT_TYPE = CONTENT_TYPE_SOAP_1_1;
	
	protected String SOAP = SOAP1_1;
	
	protected String namespace;
	protected String url;
	protected String method;
	protected String action;
	protected byte[] requestData;
	private final static String CRLF = "";
	
	public SoapRequest(String url, String namespace, String method, String action, SoapRequestObject data) {
		this.url = url;
		this.namespace = namespace;
		this.action = action;
		this.method = method;
		try {
			requestData = getRequestData(data);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.urlString = url;
		this.parser = new SoapParser();
	}
	
	private byte[] getRequestData(SoapRequestObject data) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append(CRLF);
		sb.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\""+SOAP_ENV+"\" xmlns:xsi=\"" + XSI + "\" xmlns:xsd=\"" + XSD + "\" xmlns:soap=\"" + SOAP + "\">").append(CRLF);
		sb.append(" <SOAP-ENV:Body>").append(CRLF);
		sb.append("<tns:" + method + " xmlns:tns=\"" + namespace + "\">").append(CRLF);
		makeRequestData(sb,data);
		sb.append("</tns:" + method + ">").append(CRLF);
		sb.append("</SOAP-ENV:Body>").append(CRLF);
		sb.append("</SOAP-ENV:Envelope>");
		return sb.toString().getBytes("UTF-8");
	}

	private void makeRequestData(StringBuilder sb, SoapRequestObject data) {
		// TODO Auto-generated method stub
		for(int i = 0; i < data.values.size(); i++) {
			SoapRequestValue e = data.values.get(i);
			String key = e.key;
			sb.append("<tns:" + key + ">");
			Object value = e.value;
			if (value instanceof SoapRequestObject) {
				sb.append(CRLF);
				SoapRequestObject objectValue = (SoapRequestObject)value;
				makeRequestData(sb,objectValue);
				sb.append(CRLF);
			} else {
				sb.append(value.toString());
			}
			sb.append("</tns:" + key + ">").append(CRLF);
		}
	}

	@Override
	public String getMethod() {
		return "POST";
	}

	@Override
	public boolean setConnectionConfig(HttpURLConnection conn) {
		conn.setDoOutput(true);
		conn.setUseCaches(false);		
		return true;
	}

	@Override
	public boolean setHeader(HttpURLConnection conn) {
		conn.setRequestProperty("Content-Type", CONTENT_TYPE);
		conn.setRequestProperty("SOAPAction", action);
		conn.setRequestProperty("User-Agent", "SampleWebService");
		conn.setRequestProperty("Content-Length", "" + requestData.length);
		return true;
	}

	@Override
	public boolean setOutput(HttpURLConnection conn) throws IOException {
		OutputStream output = conn.getOutputStream();
		output.write(requestData);
		output.close();
		return true;
	}
	
}
