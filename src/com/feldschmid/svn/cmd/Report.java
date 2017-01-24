package com.feldschmid.svn.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;

import com.feldschmid.svn.base.MyException;
import com.feldschmid.svn.method.HttpReport;
import com.feldschmid.svn.model.ReportList;
import com.feldschmid.svn.util.HttpClientFactory;
import com.feldschmid.svn.util.InputStreamConverter;
import com.feldschmid.svn.util.ResponseChecker;
import com.feldschmid.svn.util.URITool;
import com.feldschmid.svn.xml.ReportParser;

public class Report {

	private URI uri;
	private String user;
	private String pass;
	private boolean ignoreTrustChain;
	private int start;
	private int end;
	private int limit;
	private boolean changedpaths = false;

	public Socket s;

	public Report(String uri, String user, String pass, boolean ignoreTrustChain,
			int start, int end, int limit, boolean changedPaths) {
		uri = URITool.encode(uri);
		this.uri = URI.create(uri);
		this.user = user;
		this.pass = pass;
		this.ignoreTrustChain = ignoreTrustChain;
		this.start = start;
		this.end = end;
		this.limit = limit;
		this.changedpaths = changedPaths;
	}

	public ReportList execute() throws MyException {
		try {
			InputStream in = sendHttpReport();

			ReportParser myparser = new ReportParser(in);
			return myparser.parse();
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	private InputStream sendHttpReport() throws ClientProtocolException,
			IOException, MyException {
		HttpClient httpclient = new HttpClientFactory().getHttpClient(uri,
				user, pass, ignoreTrustChain);
		HttpReport httpreport = new HttpReport(uri);

		StringBuffer buf = new StringBuffer();
		buf.append("<S:log-report xmlns:S=\"svn:\">");
		buf.append("<S:start-revision>" + start + "</S:start-revision>");
		buf.append("<S:end-revision>" + end + "</S:end-revision>");
		buf.append("<S:limit>" + limit + "</S:limit>");
		if (changedpaths) {
			buf.append("<S:discover-changed-paths/>");
		}
		// only get report of currently selected path
		buf.append("<S:path></S:path>");
		buf.append("</S:log-report>");

		StringEntity strEntity = new StringEntity(buf.toString());
		httpreport.setEntity(strEntity);

		// Execute the request
		HttpResponse response = httpclient.execute(httpreport);
		
		// check if the response is okay
		ResponseChecker.check(response);
		
		// Get hold of the response entity
		HttpEntity entity = response.getEntity();
		return InputStreamConverter.testAndConvertToGZIP(entity.getContent(),
				response);
	}
}
