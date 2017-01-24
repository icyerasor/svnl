package com.feldschmid.svn.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import com.feldschmid.svn.base.MyException;
import com.feldschmid.svn.util.HttpClientFactory;
import com.feldschmid.svn.util.InputStreamConverter;
import com.feldschmid.svn.util.ResponseChecker;
import com.feldschmid.svn.util.URITool;

public class Get {

	private URI uri;
	private String user;
	private String pass;
	private boolean ignoreTrustChain;
	private HttpResponse response;

	public Get(String uri, String user, String pass, boolean ignoreTrustChain) {
		uri = URITool.encode(uri);
		this.uri = URI.create(uri);
		this.user = user;
		this.pass = pass;
		this.ignoreTrustChain = ignoreTrustChain;
	}
	
	public URI getURI() {
		return uri;
	}

	public InputStream execute() throws MyException {
		try {
			return sendGet();
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	private InputStream sendGet() throws IllegalStateException,
			IOException, MyException {
		HttpClient httpclient = new HttpClientFactory().getHttpClient(uri,
				user, pass, ignoreTrustChain);

		// Prepare a request object
		HttpGet httpGet = new HttpGet(uri);

		// Execute the request
		response = httpclient.execute(httpGet);
		
		// check if the response is okay
		ResponseChecker.check(response);
		
		// Get hold of the response entity
		HttpEntity entity = response.getEntity();
		// If the response does not enclose an entity, there is no need
		// to worry about connection release
		return InputStreamConverter.testAndConvertToGZIP(entity.getContent(),
				response);
	}
	
	/**
	 * @return the response that was received when calling sendGet() / execute() otherwise null
	 */
	public HttpResponse getHttpResponse() {
		return response;
	}
}
