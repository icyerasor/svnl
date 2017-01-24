package com.feldschmid.svn.method;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.params.CoreProtocolPNames;

import com.feldschmid.svn.util.URITool;


public class HttpPropfind extends HttpEntityEnclosingRequestBase {

    public final static String METHOD_NAME = "PROPFIND";
    
    public HttpPropfind() {
        super();
    }
    
    public HttpPropfind(final URI uri, int depth) {
        super();
        setURI(uri);
        setStandardHeaders(depth);
        
		// disable expect-continue because otherwise virtual hosts / subdomains
		// may have problems
        getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
    }
    
    /**
     * @throws UnsupportedEncodingException 
     * @throws IllegalArgumentException if the uri is invalid. 
     */
    public HttpPropfind(final String uri, int depth) throws UnsupportedEncodingException {
        super();
        setURI(URI.create(URITool.encode(uri)));
        setStandardHeaders(depth);
    }
    
    private void setStandardHeaders(int depth) {
    	if(depth == 1) {
    		setHeader("depth", "1");
    	}
    	else {
    		setHeader("depth", "0");
    	}
		setHeader("accept-encoding", "gzip");
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
    
}
