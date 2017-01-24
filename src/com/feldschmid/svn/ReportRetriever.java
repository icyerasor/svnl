package com.feldschmid.svn;

import java.util.List;

import com.feldschmid.svn.base.MyException;
import com.feldschmid.svn.cmd.Propfind;
import com.feldschmid.svn.cmd.Report;
import com.feldschmid.svn.model.Props;
import com.feldschmid.svn.model.ReportList;

public class ReportRetriever {

	public static ReportList retrieveReport(String uri, String user, String pass,
			boolean ignoreTrustChain, int limit, boolean changedPaths) throws MyException {
		
		Propfind p = new Propfind(uri, user, pass, ignoreTrustChain);
		List<Props> propsList = p.execute();
		Props props = propsList.get(0);
		String version = props.getVersion();
		
		Report r = new Report(uri, user, pass, ignoreTrustChain, Integer
				.valueOf(version), 1, limit, changedPaths);
		
		return r.execute();
	}
	
	public static ReportList retrieveReport(String uri, String user, String pass,
			boolean ignoreTrustChain, int start, int limit, boolean changedPaths) throws MyException {
		
		Report r = new Report(uri, user, pass, ignoreTrustChain, Integer
				.valueOf(start), 1, limit, changedPaths);
		
		return r.execute();
	}
}
