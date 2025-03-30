package gitlet;

import java.io.File;
import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Khanh Chung
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** the HEAD file */
    public static final File HEAD = join(GITLET_DIR, "HEAD");

    /* TODO: fill in the rest of this class. */
    public static void init() {
       if (!GITLET_DIR.mkdirs()) {
           message("A Gitlet version-control system already exists in the current directory.");
           System.exit(0);
       }

       Commit initialCommit = new Commit();
       String commitHash = initialCommit.saveCommit();
       Utils.writeContents(HEAD, commitHash);
       Branch.setBranchToCommitHash("master", commitHash);
    }
}
