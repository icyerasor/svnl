import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.feldschmid.svn.method.HttpPropfind;
import com.feldschmid.svn.method.HttpReport;

public class SSLHttpClientTest {
	public static void main(String[] args) throws IOException {
		doPropfind();
		// doReport();
	}

	public static void doReport() throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpReport httpreport = new HttpReport(
				"http://svn.svnkit.com/repos/svnkit/!svn/bc/5780/trunk/doc");
		httpreport.setHeader("depth", "1");
		httpreport.setHeader("accept-encoding", "gzip");

		StringBuffer buf = new StringBuffer();
		buf.append("<S:log-report xmlns:S=\"svn:\">");
		buf.append("<S:start-revision>0</S:start-revision>");
		buf.append("<S:end-revision>5780</S:end-revision>");
		// buf.append("<S:discover-changed-paths/>");
		buf.append("<S:path></S:path>");
		buf.append("</S:log-report>");

		StringEntity strEntity = new StringEntity(buf.toString());
		httpreport.setEntity(strEntity);

		HttpResponse response2 = httpclient.execute(httpreport);
		// Examine the response status
		System.out.println(response2.getStatusLine());
		// Get hold of the response entity
		HttpEntity entity = response2.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			try {

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new GZIPInputStream(instream)));
				// do something useful with the response
				String msg;
				while ((msg = reader.readLine()) != null)
					System.out.println(msg);

			} catch (IOException ex) {

				// In case of an IOException the connection will be released
				// back to the connection manager automatically
				throw ex;

			} catch (RuntimeException ex) {

				// In case of an unexpected exception you may want to abort
				// the HTTP request in order to shut down the underlying
				// connection and release it back to the connection manager.
				httpreport.abort();
				throw ex;

			} finally {

				// Closing the input stream will trigger connection release
				instream.close();

			}

			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
	}

	public static void doPropfind() throws ClientProtocolException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		URL url = new URL("https://tortoisesvn.tigris.org/svn/tortoisesvn/trunk");

		// user: guest / pw: 
		String user = in.readLine();
		String passwd = in.readLine();
		DefaultHttpClient httpclient = trustAllTrustManager(new DefaultHttpClient());
		httpclient.getCredentialsProvider().setCredentials(
                new AuthScope("tortoisesvn.tigris.org", 80), 
                new UsernamePasswordCredentials(user, passwd));


		// Prepare a request object
		HttpPropfind httpprop = new HttpPropfind(
				"http://tortoisesvn.tigris.org/svn/tortoisesvn/trunk", 0);
		httpprop.setHeader("depth", "0");
		httpprop.setHeader("accept-encoding", "gzip");

		

		
		// Execute the request
		HttpResponse response = httpclient.execute(httpprop);
		// Examine the response status
		System.out.println(response.getStatusLine());
		// Get hold of the response entity
		HttpEntity entity = response.getEntity();
		// If the response does not enclose an entity, there is no need
		// to worry about connection release
		if (entity != null) {
			InputStream instream = entity.getContent();
			try {

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(instream));
				// do something useful with the response
				String msg;
				while ((msg = reader.readLine()) != null)
					System.out.println(msg);

			} catch (IOException ex) {

				// In case of an IOException the connection will be released
				// back to the connection manager automatically
				throw ex;

			} catch (RuntimeException ex) {

				// In case of an unexpected exception you may want to abort
				// the HTTP request in order to shut down the underlying
				// connection and release it back to the connection manager.
				httpprop.abort();
				throw ex;

			} finally {

				// Closing the input stream will trigger connection release
				instream.close();

			}

			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
	}

	public static DefaultHttpClient trustAllTrustManager(
			DefaultHttpClient httpClient) {
		try {
			// First create a trust manager that won't care.
			X509TrustManager trustManager = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
					// Don't do anything.
				}

				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
					// Don't do anything.
				}

				public X509Certificate[] getAcceptedIssuers() {
					// Don't do anything.
					return null;
				}
			};

			// Now put the trust manager into an SSLContext.
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { trustManager }, null);

			// Use the above SSLContext to create your socket factory
			// (I found trying to extend the factory a bit difficult due to a
			// call to createSocket with no arguments, a method which doesn't
			// exist anywhere I can find, but hey-ho).
			SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
			sf
					.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			// If you want a thread safe client, use the ThreadSafeConManager,
			// but
			// otherwise just grab the one from the current client, and get hold
			// of its
			// schema registry. THIS IS THE KEY THING.
			ClientConnectionManager ccm = httpClient.getConnectionManager();
			SchemeRegistry schemeRegistry = ccm.getSchemeRegistry();

			// Register our new socket factory with the typical SSL port and the
			// correct protocol name.
			schemeRegistry.register(new Scheme("https", sf, 443));

			// Finally, apply the ClientConnectionManager to the Http Client
			// or, as in this example, create a new one.
			return new DefaultHttpClient(ccm, httpClient.getParams());
		} catch (Throwable t) {
			// AND NEVER EVER EVER DO THIS, IT IS LAZY AND ALMOST ALWAYS WRONG!
			t.printStackTrace();
			return null;
		}
	}
}
