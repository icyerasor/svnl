import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Suck {

	public static void main(String[] args) throws UnknownHostException, IOException {
	
		propfind();
		
		//report();
	}
	
	private static void propfind() throws UnknownHostException, IOException {
		Socket s = new Socket("svn.svnkit.com", 80);
		PrintWriter out = new PrintWriter(s.getOutputStream());
		
		out.println("PROPFIND /repos/svnkit/trunk/doc HTTP/1.1");
		out.println("Host: svn.svnkit.com");
		out.println("Depth: 0");
		out.println("");
		out.flush();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		
		String msg;
		while( (msg = in.readLine()) != null )
		{
			System.out.println(msg);
		}
	}

	private static void report() throws UnknownHostException, IOException {
		Socket s = new Socket("svn.svnkit.com", 80);
		PrintWriter out = new PrintWriter(s.getOutputStream());
		
		StringBuffer buf = new StringBuffer();
		buf.append("<S:log-report xmlns:S=\"svn:\">");
		buf.append("<S:start-revision>0</S:start-revision>");
		buf.append("<S:end-revision>5780</S:end-revision>");
		//buf.append("<S:discover-changed-paths/>");
		buf.append("<S:path></S:path>");
		buf.append("</S:log-report>");
		byte[] temp = buf.toString().getBytes();
		
		System.out.println(buf.toString());
		System.out.println(temp.length);
		
		out.println("REPORT /repos/svnkit/!svn/bc/5780/trunk/doc HTTP/1.1");
		out.println("host: svn.svnkit.com");
		out.println("depth: 1");
		out.println("content-length: "+temp.length);
		out.println("");
		
		out.print(buf.toString());
		
		out.flush();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		
		String msg;
		while( (msg = in.readLine()) != null )
		{
			System.out.println(msg);
		}
	}
	
	

}
