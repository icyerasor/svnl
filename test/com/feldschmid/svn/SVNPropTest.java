package com.feldschmid.svn;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;

import com.feldschmid.svn.base.MyException;
import com.feldschmid.svn.cmd.Propfind;
import com.feldschmid.svn.model.Props;

/**
 * Testing Propfind only
 */
public class SVNPropTest {

  @Test
  public void testProps() throws MyException, URISyntaxException {
		String uri = "https://subdroid.svn.sourceforge.net/svnroot/subdroid/branches/testBranch";

		Propfind p = new Propfind(uri, "guest", null, true);
		p.setDepth(1);

		List<Props> propsList = p.execute();
		Props props = propsList.get(0);

		System.out.println("Props: \n"+propsList);

		String version = props.getVersion();
		assertEquals("86", version);
	}
}
