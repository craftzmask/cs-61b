package bstmap;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class BSTNode {
        private K key;
        private V value;
        private BSTNode left, right;

        BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private BSTNode root;

    public BSTMap() {}

    /* Removes all of the mappings from this map. */
    public void clear() {
        root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }

    private boolean containsKey(BSTNode node, K key) {
        if (key == null || node == null) {
            return false;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            return containsKey(node.left, key);
        } else {
            return containsKey(node.right, key);
        }
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        return get(root, key);
    }

    private V get(BSTNode node, K key) {
        if (key == null || node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node.value;
        } else if (cmp < 0) {
            return get(node.left, key);
        } else {
            return get(node.right, key);
        }
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size(root);
    }

    private int size(BSTNode node) {
        if (node == null) {
            return 0;
        }

        int left = size(node.left);
        int right = size(node.right);

        return 1 + left + right;
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private BSTNode put(BSTNode node, K key, V value) {
        if (node == null) {
            return new BSTNode(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        addKeySet(keys, root);
        return keys;
    }

    private void addKeySet(Set<K> keys, BSTNode node) {
        if (node == null) {
            return;
        }
        keys.add(node.key);
        addKeySet(keys, node.left);
        addKeySet(keys, node.right);
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        V value = get(key);
        if (value != null) {
            root = remove(root, key);
        }
        return value;
    }

    private BSTNode remove(BSTNode root, K key) {
        if (root == null) {
            return null;
        }

        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            root.left = remove(root.left, key);
        } else if (cmp > 0) {
            root.right = remove(root.right, key);
        } else {
            if (root.left == null) {
                return root.right;
            }

            if (root.right == null) {
                return root.left;
            }

            BSTNode successor = getSuccessor(root);
            root.key = successor.key;
            root.right = remove(root.right, successor.key);
        }

        return root;
    }

    private BSTNode getSuccessor(BSTNode root) {
        BSTNode cur = root.right;
        while (cur != null && cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value) {
        V v = get(key);
        if (v != null && v.equals(value)) {
            root = remove(root, key);
            return v;
        }
        return null;
    }


    public void printInOrder() {
        print(root);
    }

    private void print(BSTNode root) {
        if (root == null) {
            System.out.println();
        }

        if (root.left != null) {
            print(root.left);
        }

        System.out.print(root.key + " ");

        if (root.right != null) {
            print(root.right);
        }
    }

    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator<K> implements Iterator<K> {
        private List<K> keys;

        public BSTMapIterator() {
            this.keys = (List<K>) new LinkedList<>(keySet());
        }

        public boolean hasNext() {
            return !keys.isEmpty();
        }

        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return keys.remove(0);
        }


    }
}
