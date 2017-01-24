package com.feldschmid.svn;

import static junit.framework.Assert.assertEquals;

import java.net.URISyntaxException;

import org.junit.Test;

import com.feldschmid.svn.base.MyException;
import com.feldschmid.svn.model.ReportList;

/**
 * Testing retrieveReport with a provided starting revision..
 */
public class SvnWithStart {
  @Test
  public void testSourceForge() throws URISyntaxException, MyException {
    String uri = "https://subdroid.svn.sourceforge.net/svnroot/subdroid/trunk";

    ReportList reportList = ReportRetriever.retrieveReport(uri, "guest", "", true, 47, 1, false);
    System.out.println(reportList);
    assertEquals(1, reportList.size());

    reportList = ReportRetriever.retrieveReport(uri, "guest", "", true, 84, 50, false);
    System.out.println(reportList);
    assertEquals(45, reportList.size());
  }

  @Test
  public void testGoogle() throws URISyntaxException, MyException {
    String uri = "http://subdroid.googlecode.com/svn/branches/branch-6";

    ReportList reportList = ReportRetriever.retrieveReport(uri, "guest", "", false, 10, 1, false);
    System.out.println(reportList);
    assertEquals(1, reportList.size());

    reportList = ReportRetriever.retrieveReport(uri, "guest", "", false, 10, 50, false);
    System.out.println(reportList);
    assertEquals(8, reportList.size());
  }
}
