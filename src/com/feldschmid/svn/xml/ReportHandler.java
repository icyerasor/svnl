package com.feldschmid.svn.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.feldschmid.svn.model.Action;
import com.feldschmid.svn.model.ChangedPath;
import com.feldschmid.svn.model.LogItem;
import com.feldschmid.svn.model.ReportList;
import com.feldschmid.svn.util.DateUtil;


public class ReportHandler extends DefaultHandler {

	private ReportList reportList = new ReportList();

	private boolean inDate;
	private boolean inDisplayName;
	private boolean inComment;
	private boolean inVersionName;
	private boolean inLogItem;
	private boolean inAdded;
	private boolean inModified;
	private boolean inDeleted;
	private boolean inReplaced;

	private LogItem logItem;

	private final static String LOGITEM = "log-item";

	private final static String VERSIONNAME = "version-name";
	private final static String COMMENT = "comment";
	private final static String DISPLAYNAME = "creator-displayname";
	private final static String DATE = "date";
	private final static String ADDED = "added-path";
	private final static String MODIFIED = "modified-path";
	private final static String DELETED = "deleted-path";
	private final static String REPLACED = "replaced-path";

	public ReportList getParsedData() {
		return reportList;
	}

	/**
	 * Gets be called on opening tags like: <tag> Can provide attribute(s), when
	 * xml was like: <tag attribute="attributeValue">
	 */
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		if (qName.contains(LOGITEM) || localName.contains(LOGITEM)) {
			this.inLogItem = true;
		} else if (qName.contains(VERSIONNAME) || localName.contains(VERSIONNAME)) {
			this.inVersionName = true;
		} else if (qName.contains(COMMENT) || localName.contains(COMMENT)) {
			this.inComment = true;
		} else if (qName.contains(DISPLAYNAME) || localName.contains(DISPLAYNAME)) {
			this.inDisplayName = true;
		} else if (qName.contains(DATE) || localName.contains(DATE)) {
			this.inDate = true;
		} else if(qName.contains(ADDED) || localName.contains(ADDED)) {
			this.inAdded = true;
		} else if(qName.contains(MODIFIED) || localName.contains(MODIFIED)) {
			this.inModified = true;
		} else if(qName.contains(DELETED) || localName.contains(DELETED)) {
			this.inDeleted = true;
		} else if(qName.contains(REPLACED) || localName.contains(REPLACED)) {
			this.inReplaced = true;
		}
		
		if(qName.contains(LOGITEM) || localName.contains(LOGITEM)) {
			this.logItem = new LogItem();
		}
	}

	/**
	 * Gets be called on closing tags like: </tag>
	 */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (qName.contains(LOGITEM) || localName.contains(LOGITEM)) {
			this.inLogItem = false;
		} else if (qName.contains(VERSIONNAME)  || localName.contains(VERSIONNAME)) {
			this.inVersionName = false;
		} else if (qName.contains(COMMENT) || localName.contains(COMMENT)) {
			this.inComment = false;
		} else if (qName.contains(DISPLAYNAME) || localName.contains(DISPLAYNAME)) {
			this.inDisplayName = false;
		} else if (qName.contains(DATE) || localName.contains(DATE)) {
			this.inDate = false;
		}else if(qName.contains(ADDED) || localName.contains(ADDED)) {
			this.inAdded = false;
		} else if(qName.contains(MODIFIED) || localName.contains(MODIFIED)) {
			this.inModified = false;
		} else if(qName.contains(DELETED) || localName.contains(DELETED)) {
			this.inDeleted = false;
		} else if(qName.contains(REPLACED) || localName.contains(REPLACED)) {
			this.inReplaced = false;
		}
		
		if(qName.contains(LOGITEM) || localName.contains(LOGITEM)) {
			logItem.setDate(DateUtil.parse(logItem.getDateString()));
			this.reportList.add(logItem);
		}
	}

	/**
	 * Gets be called on the following structure: <tag>characters</tag>
	 */
	@Override
	public void characters(char ch[], int start, int length) {
		if(inComment) {
			logItem.appendComment(new String(ch, start, length));
		}
		else if(inDate) {
			String date = new String(ch, start, length);
			logItem.appendDateString(date);
		}
		else if(inDisplayName) {
			logItem.appendAuthor(new String(ch, start, length));
		}
		else if(inVersionName) {
			logItem.appendVersion(new String(ch, start, length));
		} else if(inAdded) {
			logItem.addChangedPath(new ChangedPath(Action.ADD, new String(ch, start, length)));
		} else if(inModified) {
			logItem.addChangedPath(new ChangedPath(Action.MODIFY, new String(ch, start, length)));
		} else if(inDeleted) {
			logItem.addChangedPath(new ChangedPath(Action.DELETE, new String(ch, start, length)));
		} else if(inReplaced) {
			logItem.addChangedPath(new ChangedPath(Action.REPLACE, new String(ch, start, length)));
		}
	}
}
