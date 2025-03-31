package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Index implements Serializable {

    /** The index file for staging area */
    public static final File INDEX = Repository.INDEX;

    private Map<String, String> fileToBlobMap = new HashMap<>();

    public static Index getIndex() {
        if (INDEX.length() == 0) {
            return new Index();
        }
        return Utils.readObject(INDEX, Index.class);
    }

    public void saveIndex() {
        Utils.writeObject(INDEX, this);
    }

    public void addEntry(String filename, String blobHash) {
        fileToBlobMap.put(filename, blobHash);
    }

    public void removeEntry(String filename) {
        if (fileToBlobMap.containsKey(filename)) {
            fileToBlobMap.remove(filename);
        }
    }

    public boolean isEmpty() {
        return fileToBlobMap.isEmpty();
    }

    public Map<String, String> getFileToBlobMap() {
        return fileToBlobMap;
    }

    public void clear() {
        Utils.writeObject(INDEX, new Index());
    }
}
