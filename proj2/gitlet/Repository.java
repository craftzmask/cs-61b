package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static gitlet.Utils.*;

// TODO: any imports you need here

/**
 * Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author Khanh Chung
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /**
     * The commits directory
     */
    public static final File COMMIT_DIR = join(GITLET_DIR, "commits");
    /**
     * The branches directory
     */
    public static final File BRANCH_DIR = join(GITLET_DIR, "branches");
    /**
     * The blobs directory
     */
    public static final File BLOB_DIR = join(GITLET_DIR, "blobs");
    /**
     * The trees directory
     */
    public static final File TREE_DIR = join(GITLET_DIR, "trees");
    /**
     * The HEAD file
     */
    public static final File HEAD = join(GITLET_DIR, "HEAD");
    /**
     * The index file
     */
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

    public static void commit(String message) {
        if (message == null || message.isEmpty()) {
            message("Please enter a commit message.");
            System.exit(0);
        }

        Index index = Index.getIndex();
        if (index.isEmpty()) {
            message("No changes added to the commit.");
            System.exit(0);
        }

        Commit commit = getCurrentCommit();

        // Update tree
        Tree commitTree = Tree.getTree(commit.getTreeHash());
        commitTree.merge(index.getFileToBlobMap());
        String treeHash = commitTree.saveTree();

        // Update commit
        commit.setMessage(message);
        commit.setTimestamp(new Date());
        commit.setParentHash(Branch.getCommitHashFrom(getCurrentBranch()));
        commit.setTreeHash(treeHash);
        String commitHash = commit.saveCommit();

        Branch.setBranchToCommitHash(getCurrentBranch(), commitHash);
        index.clear();
    }

    public static void checkout(String commitHash, String filename) {
        File commitFile = join(COMMIT_DIR, commitHash);
        if (!commitFile.exists()) {
            message("No commit with that id exists.");
            System.exit(0);
        }

        Commit commit = Commit.fromHash(commitHash);
        Tree commitTree = Tree.getTree(commit.getTreeHash());
        String blobHash = commitTree.getBlobHashFrom(filename);

        File blobFile = join(BLOB_DIR, blobHash);
        if (!blobFile.exists()) {
            message("File does not exist in that commit.");
            System.exit(0);
        }

        String content = Utils.readContentsAsString(blobFile);
        Utils.writeContents(join(CWD, filename), content);
    }

    public static void checkout(String filename) {
        String branch = Utils.readContentsAsString(HEAD);
        String commitHash = Branch.getCommitHashFrom(branch);
        checkout(commitHash, filename);
    }

    public static void checkoutFromBranch(String branchName) {
        File branchFile = join(BRANCH_DIR, branchName);
        if (!branchFile.exists()) {
            message("No such branch exists.");
            System.exit(0);
        }

        if (getCurrentBranch().equals(branchName)) {
            message("No need to checkout the current branch.");
            System.exit(0);
        }

        // Get current commit
        Commit currentCommit = getCurrentCommit();
        Tree currentTree = Tree.getTree(currentCommit.getTreeHash());

        // Get commit from the given branch
        String commitHash = Branch.getCommitHashFrom(branchName);
        Commit commit = Commit.fromHash(commitHash);
        Tree tree = Tree.getTree(commit.getTreeHash());

        // Check if any file from current working dir not included in the current commit
        List<String> workingFiles = plainFilenamesIn(CWD);
        for (String file : workingFiles) {
            boolean isUntracked = !currentTree.containsFile(file);
            boolean willBeOverwritten = tree.containsFile(file);
            if (isUntracked && willBeOverwritten) {
                message("There is an untracked file in the way; delete it, or add and commit it first.");
                System.exit(0);
            }
        }

        // Delete all files from the current working dir
        for (String file : workingFiles) {
            join(CWD, file).delete();
        }

        // Copy everything from the given commit to the current working dir
        for (var entry : tree.getFileToBlobMap().entrySet()) {
            File blobFile = join(BLOB_DIR, entry.getValue());
            writeContents(join(CWD, entry.getKey()), Utils.readContentsAsString(blobFile));
        }

        Index.getIndex().clear();
        Index.getIndex().saveIndex();
        Utils.writeContents(join(GITLET_DIR, "HEAD"), branchName);
    }

    public static void log() {
        String commitHash = Branch.getCommitHashFrom(getCurrentBranch());
        Commit commit = Commit.fromHash(commitHash);
        while (commit != null) {
            commit.printCommit(commitHash);
            if (commit.getParentHash().isEmpty()) {
                return;
            }
            commitHash = commit.getParentHash();
            commit = Commit.fromHash(commitHash);
        }
    }

    public static void branch(String name) {
        File branchFile = join(BRANCH_DIR, name);
        if (branchFile.exists()) {
            message("A branch with that name already exists.");
            System.exit(0);
        }

        Utils.writeContents(branchFile, getCurrentCommitHash());
    }

    public static void deleteBranch(String name) {
        File branchFile = join(BRANCH_DIR, name);
        if (!branchFile.exists()) {
            message("A branch with that name does not exist.");
            System.exit(0);
        }

        if (getCurrentBranch().equals(name)) {
            message("Cannot remove the current branch.");
            System.exit(0);
        }

        branchFile.delete();
    }

    public static void globalLog() {
        List<String> commitHashList = plainFilenamesIn(COMMIT_DIR);
        for (String commitHash : commitHashList) {
            Commit.fromHash(commitHash).printCommit(commitHash);
        }
    }

    public static void find(String commitMessage) {
        boolean found = false;
        List<String> commitHashList = plainFilenamesIn(COMMIT_DIR);
        for (String commitHash : commitHashList) {
            Commit commit = Commit.fromHash(commitHash);
            if (commit.getMessage().equals(commitMessage)) {
                System.out.println(commitHash);
                found = true;
            }
        }

        if (!found) {
            message("Found no commit with that message.");
            System.exit(0);
        }
    }

    public static void rm(String filename) {
        Index index = Index.getIndex();
        if (index.containsFile(filename)) {
            index.removeEntry(filename);
            index.saveIndex();
            System.exit(0);
        }

        Commit commit = getCurrentCommit();
        Tree tree = Tree.getTree(commit.getTreeHash());
        if (tree.containsFile(filename)) {
            index.addEntry(filename, ""); // empty blob means will be stage for removal
            File f = join(CWD, filename);
            if (f.exists()) {
                f.delete();
            }
            index.saveIndex();
            System.exit(0);
        }

        Utils.message("No reason to remove the file.");
        System.exit(0);
    }

    public static void status() {
        List<String> branches = plainFilenamesIn(BRANCH_DIR);
        Collections.sort(branches);
        System.out.println("=== Branches ===");
        for (var s : branches) {
            String currentBranch = getCurrentBranch();
            if (currentBranch.equals(s)) {
                System.out.print("*");
            }
            System.out.println(s);
        }
        System.out.println();

        printList("Staged Files", Index.getIndex().getStagedFiles());
        printList("Removed Files", Index.getIndex().getRemovedFiles());
        printList("Modifications Not Staged For Commit", new ArrayList<>());
        printList("Untracked Files", new ArrayList<>());
    }

    public static boolean isInitialized() {
        return GITLET_DIR.exists();
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
        return Commit.fromHash(getCurrentCommitHash());
    }

    private static String getCurrentCommitHash() {
        String branch = Utils.readContentsAsString(HEAD);
        return Branch.getCommitHashFrom(branch);
    }

    private static String getCurrentBranch() {
        return Utils.readContentsAsString(HEAD);
    }

    private static void printList(String title, List<String> list) {
        Collections.sort(list);
        System.out.println("=== " + title + " ===");
        for (var s : list) {
            System.out.println(s);
        }
        System.out.println();
    }
}
