import java.util.Collection;
import java.util.Iterator;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class History {

	public static void main( String[] args ) throws SVNException {
        DAVRepositoryFactory.setup( );

        String url = "http://svn.svnkit.com/repos/svnkit/trunk/doc";
        String name = "anonymous";
        String password = "anonymous";
        long startRevision = 0;
        long endRevision = -1; //HEAD (the latest) revision

        SVNRepository repository = null;
        try {
            repository = SVNRepositoryFactory.create( SVNURL.parseURIEncoded( url ) );
            ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager( name, password );
            repository.setAuthenticationManager( authManager );

            Collection logEntries = null;
            
            logEntries = repository.log( new String[] { "" } , null , startRevision , endRevision , true , true );
            Iterator iter = logEntries.iterator();
            
            while(iter.hasNext()) {
            	SVNLogEntry entry = (SVNLogEntry) iter.next();
            	System.out.println(entry.getRevision());
            	System.out.println(entry.getDate()+" : "+entry.getAuthor());
            	System.out.println(" "+entry.getMessage());
            	System.out.println("=====================================");
            }
            
        }
        finally {
        	
        }
    }
}