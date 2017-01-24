import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.feldschmid.svn.util.DateUtil;


public class DateTest {

	public static void main(String[] args) throws ParseException {
		String url = "svn.svnkit.com/test";
		String host = url.substring(0, url.indexOf("/"));
		String path = url.substring(url.indexOf("/"), url.length());
		
		System.out.println("Host: "+host+", path: "+path);
		
		System.out.println(DateUtil.parse("2009-04-07T09:56:52.300136Z"));
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'");
		Date dp = df.parse("2009-04-07T09:56:52.300136Z");
		System.out.println(dp);
		
	}
}
