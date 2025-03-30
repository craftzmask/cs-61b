package gitlet;

import java.io.File;
import java.io.IOException;

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
    /** The commits directory */
    public static final File COMMIT_DIR = join(GITLET_DIR, "commits");
    /** The branches directory */
    public static final File BRANCH_DIR = join(GITLET_DIR, "branches");
    /** The blobs directory */
    public static final File BLOB_DIR = join(GITLET_DIR, "blobs");
    /** The trees directory */
    public static final File TREE_DIR = join(GITLET_DIR, "trees");
    /** The HEAD file */
    public static final File HEAD = join(GITLET_DIR, "HEAD");
    /** The index file */
    public static final File INDEX = join(GITLET_DIR, "index");

    /* TODO: fill in the rest of this class. */
    public static void init() throws IOException {
       if (!setup()) {
           message("A Gitlet version-control system already exists in the current directory.");
           System.exit(0);
       }

       Commit initialCommit = new Commit();
       String commitHash = initialCommit.saveCommit();
       Branch.setBranchToCommitHash("master", commitHash);
       Utils.writeContents(HEAD, "master");
    }

    public static void add(String filename) {
        File f = join(CWD, filename);
        if (!f.exists()) {
            message("File does not exist.");
            System.exit(0);
        }

        String content = Utils.readContentsAsString(f);
        String blobHash = Utils.sha1(content);
        Index index = Index.getIndex();

        // Get current commit hash
        Commit commit = getCurrentCommit();
        if (commit != null) {
            Tree tree = Tree.getTree(commit.getTreeHash());
            String treeBlobHash = tree.getBlobHashFrom(filename);
            if (treeBlobHash.equals(blobHash)) {
                index.removeEntry(filename);
                index.saveIndex();
                return;
            }
        }

        File blobFile = join(BLOB_DIR, blobHash);
        if (!blobFile.exists()) {
            Utils.writeContents(blobFile, content);
        }

        index.addEntry(filename, blobHash);
        index.saveIndex();
    }

    private static boolean setup() throws IOException {
        if (GITLET_DIR.mkdirs()) {
            COMMIT_DIR.mkdirs();
            BRANCH_DIR.mkdirs();
            BLOB_DIR.mkdirs();
            TREE_DIR.mkdirs();
            INDEX.createNewFile();
            return true;
        }

        return false;
    }

    private static Commit getCurrentCommit() {
        String branch = Utils.readContentsAsString(HEAD);
        String commitHash = Branch.getCommitHashFrom(branch);
        return Commit.fromHash(commitHash);
    }
}
