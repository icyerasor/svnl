package com.feldschmid.svn.method;
import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.params.CoreProtocolPNames;

import com.feldschmid.svn.util.URITool;


public class HttpReport extends HttpEntityEnclosingRequestBase {

    public final static String METHOD_NAME = "REPORT";
    
    public HttpReport() {
        super();
    }
    
    public HttpReport(final URI uri) {
        super();
        setURI(uri);
        setStandardHeaders();
        
		// disable expect-continue because otherwise virtual hosts / subdomains
		// may have problems
        getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
    }
    
    /**
     * @throws IllegalArgumentException if the uri is invalid. 
     */
    public HttpReport(final String uri) {
        super();
        setURI(URI.create(URITool.encode(uri)));
        setStandardHeaders();
    }
    
    private void setStandardHeaders() {
		setHeader("depth", "1");
		setHeader("accept-encoding", "gzip");
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
    
}
