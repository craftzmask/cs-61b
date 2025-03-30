package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Khanh Chung
 */
public class Commit implements Serializable {

    /** Folder that commits live in. */
    static final File COMMIT_DIR = Repository.COMMIT_DIR;

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

    /* TODO: fill in the rest of this class. */
    /**
     * Creates an initial commit.
     */
    public Commit() {
        message = "initial commit";
        timestamp = (new Date(0)).toString();
        parentHash = "";
        secondParentHash = "";
        treeHash = "";
    }

    /**
     * Reads in and deserializes a commit from a file with name HASH in COMMIT_FOLDER.
     *
     * @param hash Hash of commit to load
     * @return Commit read from file
     */
    public static Commit fromHash(String hash) {
        File f = Utils.join(COMMIT_DIR, hash);
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
        this.timestamp = timestamp.toString();
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
}
