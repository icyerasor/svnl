import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.feldschmid.svn.method.HttpPropfind;
import com.feldschmid.svn.method.HttpReport;


public class HttpClientTest {
	public static void main(String[] args) throws IOException {
		doPropfind();
		doReport();
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
		//buf.append("<S:discover-changed-paths/>");
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
		HttpClient httpclient = new DefaultHttpClient();

		// Prepare a request object
		HttpPropfind httpprop = new HttpPropfind(
				"http://svn.svnkit.com/repos/svnkit/trunk/doc", 0);
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
}
