package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree implements Serializable {
    /** Folder that trees live in. */
    static final File TREE_DIR = Repository.TREE_DIR;

    private Map<String, String> fileToBlobMap = new HashMap<>();

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

    public String getBlobHashFrom(String filename) {
        return fileToBlobMap.getOrDefault(filename, "");
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
