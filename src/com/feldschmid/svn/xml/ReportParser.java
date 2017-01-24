package com.feldschmid.svn.xml;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.feldschmid.svn.base.MyException;
import com.feldschmid.svn.model.ReportList;



public class ReportParser {
	
	private InputStream in;

	public ReportParser(InputStream in) {
		this.in = in;
	}
	
	public ReportList parse() throws MyException {
		try {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		XMLReader reader = parser.getXMLReader();
		
		ReportHandler handler = new ReportHandler();
		
		reader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
		reader.setFeature("http://xml.org/sax/features/namespaces", false);
		
		reader.setContentHandler(handler);
		reader.parse(new InputSource(in));
		
		return handler.getParsedData();
		}
		catch (Exception e) {
			throw new MyException(e);
		}
		
	}

}
