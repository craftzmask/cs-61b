package gitlet;

import java.io.File;

public class Branch {

    /** Folder that branches live in. */
    static final File BRANCH_DIR = Utils.join(Repository.GITLET_DIR, "branches");

    /**
     *  Set the branch to point to the commit hash
     *
     * @param name Name of the branch
     * @param commitHash the commit hash that the branch will point to
     */
    public static void setBranchToCommitHash(String name, String commitHash) {
        BRANCH_DIR.mkdirs();
        File f = Utils.join(BRANCH_DIR, name);
        Utils.writeContents(f, commitHash);
    }

    /**
     *  Returns commit hash from the branch with name NAME in BRANCH_FOLDER.
     *
     * @param name Name of the branch
     * @return Commit hash that the branch points to
     */
    public static String getCommitHashFrom(String name) {
        File f = Utils.join(BRANCH_DIR, name);
        return Utils.readContentsAsString(f);
    }
}
