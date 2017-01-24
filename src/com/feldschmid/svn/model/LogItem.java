package com.feldschmid.svn.model;

import java.util.ArrayList;
import java.util.List;

public class LogItem {

	private String author = "";
	private String comment = "";
	private String version = "";
	private String dateString = "";
	private String date = "";
	private List<ChangedPath> paths = new ArrayList<ChangedPath>();

	public String getAuthor() {
		return author;
	}

	public void appendAuthor(String author) {
		if(!"\n".equals(author)) {
			this.author += author;
		}
	}

	public String getComment() {
		return comment;
	}

	public void appendComment(String comment) {
		if (comment != null) {
			this.comment += comment;
		}
	}

	public String getVersion() {
		return version;
	}

	public void appendVersion(String version) {
		if(!"\n".equals(version)) {
			this.version += version;
		}
	}
	
	public void appendDateString(String date) {
		this.dateString += date;
	}
	
	public String getDateString() {
		return dateString;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		if(!"\n".equals(date)) {
			this.date += date;
		}
	}
	
	public void addChangedPath(ChangedPath path) {
		paths.add(path);
	}
	
	public List<ChangedPath> getChangedPaths() {
		return paths;
	}

	public String toString() {
		return "Version: " + version + ", Author: " + author + ", Comment: "
				+ comment + ", Date: " + date + ", Paths: " + paths.toString()
				+ "\r\n";
	}

}
