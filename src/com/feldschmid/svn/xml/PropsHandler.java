package com.feldschmid.svn.xml;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.feldschmid.svn.model.Props;


public class PropsHandler extends DefaultHandler {

	private Props props;
	private List<Props> propList = new ArrayList<Props>();

	private final static String RESPONSE = "response";
	private final static String BR_PATH = "baseline-relative-path";
	private final static String VERSIONNAME = "version-name";
	private final static String DISPLAYNAME = "creator-displayname";
	private final static String CREATIONDATE = "creationdate";
	private final static String LASTMODIFIEDDATE = "getlastmodified";
	private final static String CONTENTTYPE ="getcontenttype";

	// for folders:
	// String <lp1:resourcetype><D:collection/></lp1:resourcetype>
	// for files its empty resourcetype
	private final static String COLLECTION = "collection";

	private boolean inBRPath;
	private boolean inVersionName;
	private boolean inDisplayName;
	private boolean inCreationDate;
	private boolean inLastModifiedDate;
	private boolean inContentType;

	public List<Props> getParsedData() {
		return propList;
	}

	/**
	 * Gets be called on opening tags like: <tag> Can provide attribute(s), when
	 * xml was like: <tag attribute="attributeValue">
	 */
	@Override
  public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {

		if (qName.contains(RESPONSE) || localName.contains(RESPONSE)) {
			this.props = new Props();
		} else if (qName.contains(BR_PATH) || localName.contains(BR_PATH)) {
			inBRPath = true;
		} else if (qName.contains(VERSIONNAME) || localName.contains(VERSIONNAME)) {
			inVersionName = true;
		} else if (qName.contains(DISPLAYNAME) || localName.contains(DISPLAYNAME)) {
			inDisplayName = true;
		} else if (qName.contains(CREATIONDATE) || localName.contains(CREATIONDATE)) {
			inCreationDate = true;
		} else if (qName.contains(LASTMODIFIEDDATE) || localName.contains(LASTMODIFIEDDATE)) {
			inLastModifiedDate = true;
		} else if (qName.contains(CONTENTTYPE) || localName.contains(CONTENTTYPE)) {
			inContentType = true;
		} else if(qName.contains(COLLECTION) || localName.contains(COLLECTION)) {
		    props.setCollection(true);
		}

	}

	/**
	 * Gets be called on closing tags like: </tag>
	 */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {

		if (qName.contains(RESPONSE) || localName.contains(RESPONSE)) {
			propList.add(props);
		} else if (qName.contains(BR_PATH) || localName.contains(BR_PATH)) {
			inBRPath = false;
		} else if (qName.contains(VERSIONNAME) || localName.contains(VERSIONNAME)) {
			inVersionName = false;
		} else if (qName.contains(DISPLAYNAME) || localName.contains(DISPLAYNAME)) {
			inDisplayName = false;
		} else if (qName.contains(CREATIONDATE) || localName.contains(CREATIONDATE)) {
			inCreationDate = false;
		} else if (qName.contains(LASTMODIFIEDDATE) || localName.contains(LASTMODIFIEDDATE)) {
			inLastModifiedDate = false;
		} else if (qName.contains(CONTENTTYPE) || localName.contains(CONTENTTYPE)) {
			inContentType = false;
        } else if(qName.contains(COLLECTION) || localName.contains(COLLECTION)) {
          // nothing to do
      }
	}

	/**
	 * Gets be called on the following structure: <tag>characters</tag>
	 */
	@Override
	public void characters(char ch[], int start, int length) {
		if (inDisplayName) {
			props.appendAuthor(new String(ch, start, length));
		} else if (inVersionName) {
			props.appendVersion(new String(ch, start, length));
		} else if (inBRPath) {
			props.appendHref(new String(ch, start, length));
		} else if (inCreationDate) {
			props.appendCreationDateString(new String(ch, start, length));
		} else if (inLastModifiedDate) {
			props.appendLastModifiedDateString(new String(ch, start, length));
		} else if (inContentType) {
			props.appendContentType(new String(ch, start, length));
		}
	}

}
