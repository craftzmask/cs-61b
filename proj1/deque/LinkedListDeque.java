package deque;

public class LinkedListDeque<T> {
    private static class Node<T> {
        public T item;
        public Node<T> prev;
        public Node<T> next;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<T> sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node<>(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        Node<T> newNode = new Node<>(item, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    public void addLast(T item) {
        Node<T> newNode = new Node<>(item, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node<T> p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node<T> node = sentinel.next;
        sentinel.next = node.next;
        node.next.prev = sentinel;
        size--;
        return node.item;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        Node<T> node = sentinel.prev;
        node.prev.next = node.next;
        sentinel.prev = node.prev;
        size--;
        return node.item;
    }

    public T get(int index) {
        Node<T> p = sentinel.next;
        while (index != 0 && p != sentinel) {
            p = p.next;
            index--;
        }
        return p.item;
    }


    private T getHelper(Node<T> p, int index) {
        if (p == sentinel || index == 0) {
            return p.item;
        }
        return getHelper(p.next, index - 1);
    }

    public T getRecursive(int index) {
        return getHelper(sentinel.next, index);
    }
}
