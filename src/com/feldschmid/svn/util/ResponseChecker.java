package com.feldschmid.svn.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import com.feldschmid.svn.base.MyException;

public class ResponseChecker {

	public static void check(HttpResponse response) throws MyException {

		int code = response.getStatusLine().getStatusCode();

		switch (code) {
		case HttpStatus.SC_OK:
			return;
		case HttpStatus.SC_MULTI_STATUS:
			return;
		case HttpStatus.SC_NOT_FOUND:
			throw new MyException("HTTP Response: 404. Ressource not found. Check the URL for this repository (Hint: /trunk should be set in path, not inside base-url!)", new MyException(response.getStatusLine().toString()));
		case HttpStatus.SC_INTERNAL_SERVER_ERROR:
			throw new MyException("HTTP Response: 500. Internal Server error. Check the URL, user and pass for this repository.", new MyException(response.getStatusLine().toString()));
		case HttpStatus.SC_UNAUTHORIZED:
			throw new MyException("HTTP Response: 401. Authorization Required. Check the user and pass for this repository.", new MyException(response.getStatusLine().toString()));
		default:
			throw new MyException("HTTP Response: "+code+" "+response.getStatusLine().toString());
		}

	}

}
