package com.feldschmid.svn;

import static junit.framework.Assert.assertEquals;

import java.net.URISyntaxException;

import org.junit.Test;

import com.feldschmid.svn.base.MyException;
import com.feldschmid.svn.model.ReportList;


/**
 * Basically the same as SVNBranchTest, only also retrieve changedPaths (which files were modified and how (add, del,
 * modify)
 *
 * @author yci
 */
public class SvnPathsTest {

  @Test
  public void testBranchSourceforge() throws URISyntaxException, MyException {
    String uri = "https://subdroid.svn.sourceforge.net/svnroot/subdroid/branches/testBranch";

    ReportList reportList = ReportRetriever.retrieveReport(uri, "guest", "", true, 1, true);
    System.out.println(reportList);
    assertEquals(1, reportList.size());

    reportList = ReportRetriever.retrieveReport(uri, "guest", "", true, 50, true);
    System.out.println(reportList);
    assertEquals(24, reportList.size());
  }

  @Test
  public void testBranchGoogle() throws URISyntaxException, MyException {
    String uri = "http://subdroid.googlecode.com/svn/branches/branch-6";

    ReportList reportList = ReportRetriever.retrieveReport(uri, "", "", false, 1, true);
    System.out.println(reportList);
    assertEquals(1, reportList.size());

    reportList = ReportRetriever.retrieveReport(uri, "", "", false, 50, true);
    System.out.println(reportList);
    assertEquals(9, reportList.size());
  }

  @Test
  public void testBranchTortoise() throws MyException {
    String uri = "http://tortoisesvn.tigris.org/svn/tortoisesvn/branches/1.6.x/";

    ReportList reportList = ReportRetriever.retrieveReport(uri, "guest", "", false, 1, true);
    System.out.println(reportList);
    assertEquals(1, reportList.size());

    reportList = ReportRetriever.retrieveReport(uri, "guest", "", false, 50, true);
    System.out.println(reportList);
    assertEquals(50, reportList.size());
  }
}
