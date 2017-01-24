package com.feldschmid.svn.xml;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.feldschmid.svn.base.MyException;
import com.feldschmid.svn.model.Props;



public class PropfindParser {
	
	private InputStream in;

	public PropfindParser(InputStream in, boolean extended) {
		this.in = in;
	}
	
	public List<Props> parse() throws MyException {
		try {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		XMLReader reader = parser.getXMLReader();
		
		PropsHandler handler = new PropsHandler();
		
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
