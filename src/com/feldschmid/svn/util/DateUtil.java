package com.feldschmid.svn.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static String parse(String dateString) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'");
			Date d = df.parse(dateString);
			return new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").format(d);
			
		}
		catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
