package com.example.testnetworksample2.parser;

import java.io.InputStream;

abstract public class InputStreamParser {
	abstract public void doParse(InputStream is) throws InputStreamParserException;
	abstract public Object getResult();
}
