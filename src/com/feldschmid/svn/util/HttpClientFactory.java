package com.feldschmid.svn.util;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpProtocolParams;

import com.feldschmid.svn.base.MyException;

public class HttpClientFactory {

	private int port;
	private String scheme = "http";
	private DefaultHttpClient httpclient;

	public HttpClient getHttpClient(URI uri, String user, String pass,
			boolean ignoreTrustChain) throws MyException {
		processURI(uri);

		if (!ignoreTrustChain) {
			httpclient = new DefaultHttpClient();
		} else {
			try {
				httpclient = getIgnoreTrustChainHttpClient();
			} catch (KeyManagementException e) {
				throw new MyException(e);
			} catch (NoSuchAlgorithmException e) {
				throw new MyException(e);
			}
		}

		configureUserPass(httpclient, uri, user, pass);
	    HttpProtocolParams.setUserAgent(httpclient.getParams(), "SVN/1.6.1/TortoiseSVN-1.6.1");
		return httpclient;
	}

	public HttpClient getHTTPClient(URI uri, String user, String pass)
			throws MyException {
		return getHttpClient(uri, user, pass, false);
	}

	/**
	 * Configures the user/pass that should be used for this HttpClient for the
	 * host given within the uri.
	 *
	 * @param client
	 * @param uri
	 * @param user
	 * @param pass
	 */
	private void configureUserPass(DefaultHttpClient client, URI uri,
			String user, String pass) {
		if (user != null) {
			client.getCredentialsProvider().setCredentials(
					new AuthScope(uri.getHost(), port),
					new UsernamePasswordCredentials(user, pass));
		}
	}

	/**
	 * Processes an URI and tries to find the correct port.
	 * <p>
	 * For HTTP port 80 is assumed is no explicit port is defined. <br />
	 * For HTTPS port 443 is assumed if no explicit port is defined.
	 * </p>
	 *
	 * @param uri
	 */
	private void processURI(URI uri) {
		if (uri.getScheme() != null) {
			this.scheme = uri.getScheme();
		}

		if (uri.getPort() != -1) {
			this.port = uri.getPort();
		} else if ("http".equalsIgnoreCase(scheme)) {
			this.port = 80;
		} else if ("https".equalsIgnoreCase(scheme)) {
			this.port = 443;
		}
	}

	private DefaultHttpClient getIgnoreTrustChainHttpClient()
			throws NoSuchAlgorithmException, KeyManagementException {
		DefaultHttpClient httpClient = new DefaultHttpClient();

		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme(scheme, new EasySSLSocketFactory(), port));
		ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(
				httpClient.getParams(), registry);
		return new DefaultHttpClient(manager, httpClient.getParams());

	}
}
