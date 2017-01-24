package com.feldschmid.svn.base;

public class MyException extends Exception {

	private static final long serialVersionUID = 1L;

	public MyException(Exception e) {
		super(e.getMessage(), e);
	}

	public MyException(String s) {
		super(s);
	}

	public MyException(String s, Exception e) {
		super(s, e);
	}
}
