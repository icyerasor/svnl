package com.feldschmid.svn.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HTTP;

public class InputStreamConverter {

	private final static String GZIP = "gzip";

	/**
	 * 
	 * @param in
	 * @param firstHeader
	 * @return
	 * @throws IOException
	 */
	public static InputStream testAndConvertToGZIP(InputStream in,
			HttpResponse response) throws IOException {
		Header firstHeader = response.getFirstHeader(HTTP.CONTENT_ENCODING);
		if (firstHeader != null
				&& GZIP.equalsIgnoreCase(firstHeader.getValue())) {
			return new GZIPInputStream(in);
		}
		return in;
	}

}
