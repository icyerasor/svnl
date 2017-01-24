package com.feldschmid.svn.util;

import java.util.Comparator;

import com.feldschmid.svn.model.Props;

public class PropsComparator implements Comparator<Props>{

	public int compare(Props o1, Props o2) {
		String h1 = o1.getHref();
		String h2 = o2.getHref();
		
		if(h1.equals(h2)) {
			return 0;
		}
		
		// ".." has special meaning only h1 or h2 will be "..", .. is always the greatest
		if(h1.equals("..")) {
			return -1;
		}
		if(h2.equals("..")) {
			return 1;
		}
		
		// sort filenames
		if(h1.contains(".") && h2.contains(".")) {
			return h1.compareTo(h2);
		}
		
		// sort non filenames
		if(!h1.contains(".") && !h2.contains(".")) {
			return h1.compareTo(h2);
		}
		
		// sort mixes of filename non-filename
		if (h1.contains(".") && !h2.contains(".")) {
			return 1;
		} else {
			return -1;
		}
	}

}
