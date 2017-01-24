import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Brew {

	public static void main(String[] args) {

		try {

			URL url = new URL("https://subdroid.svn.sourceforge.net/svnroot/subdroid/trunk");
			System.out.println(url.getProtocol()+"://"+url.getHost()+url.getPort());
			System.out.println(url.getPath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//System.out.println(conn.getHeaderFields());
			//System.out.println(conn.getHeaderField(null));
			conn.setRequestMethod("GET");
			conn.setRequestProperty("URI", "/repos/svnkit/trunk/doc");
			
//			 conn.setDoOutput(true);
//			 OutputStreamWriter wr = new
//			 OutputStreamWriter(conn.getOutputStream());
//			 wr.write(""); wr.flush();

			Object o = conn.getContent();
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn
					.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) { // Process line...
				System.out.println(line);
			}
			
			System.out.println(conn.getHeaderFields());
			// wr.close();
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}