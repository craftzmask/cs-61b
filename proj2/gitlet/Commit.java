package gitlet;

import java.io.File;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/** Represents a gitlet commit object.
 *  does at a high level.
 *
 *  @author Khanh Chung
 */
public class Commit implements Serializable {

    /** Folder that commits live in. */
    static final File COMMIT_DIR = Repository.COMMIT_DIR;

    static final DateTimeFormatter FORMATTER = DateTimeFormatter
            .ofPattern("EEE MMM dd HH:mm:ss yyyy Z")
            .withZone(ZoneId.of("America/Los_Angeles"));

    /** The message of this Commit. */
    private String message;
    /** The timestamp of this Commit */
    private String timestamp;
    /** The parent commit hash */
    private String parentHash;
    /** The second parent commit hash */
    private String secondParentHash;
    /** The tree hash */
    private String treeHash;

    /**
     * Creates an initial commit.
     */
    public Commit() {
        message = "initial commit";
        timestamp = FORMATTER.format((new Date(0)).toInstant());
        parentHash = "";
        secondParentHash = "";
        treeHash = (new Tree()).saveTree();
    }

    /**
     * Reads in and deserializes a commit from a file with name HASH in COMMIT_FOLDER.
     *
     * @param hash Hash of commit to load
     * @return Commit read from file
     */
    public static Commit fromHash(String hash) {
        File f = Utils.join(COMMIT_DIR, hash);
        if (!f.exists()) {
            Utils.message("No commit with that id exists.");
            System.exit(0);
        }

        return Utils.readObject(f, Commit.class);
    }

    /**
     * Hash the commit and saves to a file with the name its hash string
     *
     * @return Hash of the commit
     */
    public String saveCommit() {
        String hash = Utils.sha1(message, timestamp, parentHash, secondParentHash, treeHash);
        File f = Utils.join(COMMIT_DIR, hash);
        Utils.writeObject(f, this);
        return hash;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = FORMATTER.format(timestamp.toInstant());
    }

    public void setParentHash(String parentHash) {
        this.parentHash = parentHash;
    }

    public void setSecondParentHash(String secondParentHash) {
        this.secondParentHash = secondParentHash;
    }

    public void setTreeHash(String treeHash) {
        this.treeHash = treeHash;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getParentHash() {
        return parentHash;
    }

    public String getSecondParentHash() {
        return secondParentHash;
    }

    public String getTreeHash() {
        return treeHash;
    }

    public void printCommit(String hash) {
        System.out.println("===");
        System.out.println("commit " + hash);
        if (!secondParentHash.isEmpty()) {
            System.out.println("Merge: " + parentHash.substring(0, 7)
                    + " " + secondParentHash.substring(0, 7));
        }
        System.out.println("Date: " + timestamp);
        System.out.println(message);
        System.out.println();
    }
}
