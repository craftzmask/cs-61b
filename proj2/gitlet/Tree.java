package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

public class Tree implements Serializable {
    /** Folder that trees live in. */
    static final File TREE_DIR = Repository.TREE_DIR;

    private Map<String, String> fileToBlobMap;

    public Tree() {
        this.fileToBlobMap = new TreeMap<>();
    }

    public static Tree getTree(String hash) {
        File f = Utils.join(TREE_DIR, hash);
        return Utils.readObject(f, Tree.class);
    }

    public String saveTree() {
        String hash = Utils.sha1(data());
        File f = Utils.join(TREE_DIR, hash);
        Utils.writeObject(f, this);
        return hash;
    }

    public void merge(Map<String, String> other) {
        fileToBlobMap.putAll(other);
        fileToBlobMap.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }

    public String getBlobHashFrom(String filename) {
        String blobHash = fileToBlobMap.getOrDefault(filename, "");
        if (blobHash.isEmpty()) {
            Utils.message("File does not exist in that commit.");
            System.exit(0);
        }
        return blobHash;
    }

    public Map<String, String> getFileToBlobMap() {
        return fileToBlobMap;
    }

    public boolean containsFile(String filename) {
        return fileToBlobMap.containsKey(filename);
    }

    private List<Object> data() {
        List<Object> data = new ArrayList<>();
        for (String key : fileToBlobMap.keySet()) {
            data.add(key);
            data.add(fileToBlobMap.get(key));
        }
        return data;
    }
}
