package com.feldschmid.svn.model;

public class ChangedPath {

	private String path;
	private Action action;

	public ChangedPath(Action action, String path) {
		this.action = action;
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
	public String toString() {
		return "Action: "+action+", Path: "+path;
	}

}
