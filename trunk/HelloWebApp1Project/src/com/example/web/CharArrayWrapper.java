package com.example.web;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CharArrayWrapper extends HttpServletResponseWrapper {

	private CharArrayWriter charWriter;
	private PrintWriter writer;
	
	public CharArrayWrapper(HttpServletResponse response) {
		super(response);
		charWriter = new CharArrayWriter();
		writer = new PrintWriter(charWriter);
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return writer;
	}
	
	@Override
	public String toString() {
		return (charWriter.toString());
	}
	
	public char[] toCharArray() {
		return (charWriter.toCharArray());
	}
}
