package com.feldschmid.svn.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import com.feldschmid.svn.base.MyException;
import com.feldschmid.svn.method.HttpPropfind;
import com.feldschmid.svn.model.Props;
import com.feldschmid.svn.util.HttpClientFactory;
import com.feldschmid.svn.util.InputStreamConverter;
import com.feldschmid.svn.util.ResponseChecker;
import com.feldschmid.svn.util.URITool;
import com.feldschmid.svn.xml.PropfindParser;

public class Propfind {

	private URI uri;
	private String user;
	private String pass;
	private boolean ignoreTrustChain;
	private int depth = 0;

	public Propfind(String uri, String user, String pass, boolean ignoreTrustChain) {
		uri = URITool.encode(uri);
		this.uri = URI.create(uri);
		this.user = user;
		this.pass = pass;
		this.ignoreTrustChain = ignoreTrustChain;
	}

	public List<Props> execute() throws MyException {
		try {
			InputStream in = sendHttpPropfind();

			PropfindParser myparser = new PropfindParser(in, depth!=0);

			return myparser.parse();
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	/**
	 * Defaults to 0: the propfind will only return the props of the resource it
	 * directly refers to, when set to 1, will also return props of children.
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	private InputStream sendHttpPropfind() throws IllegalStateException,
			IOException, MyException {
		HttpClient httpclient = new HttpClientFactory().getHttpClient(uri,
				user, pass, ignoreTrustChain);

		// Prepare a request object
		HttpPropfind httpprop = new HttpPropfind(uri, depth);

		// Execute the request
		HttpResponse response = httpclient.execute(httpprop);
		
		// check if the response is okay
		ResponseChecker.check(response);
		
		// Get hold of the response entity
		HttpEntity entity = response.getEntity();
		// If the response does not enclose an entity, there is no need
		// to worry about connection release
		return InputStreamConverter.testAndConvertToGZIP(entity.getContent(),
				response);
	}
}
