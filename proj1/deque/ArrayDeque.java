package deque;

public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int k = nextFirst + 1;
        for (int i = 0; i < size(); i++) {
            if (k == items.length) {
                k = 0;
            }
            a[i] = items[k];
            k++;
        }
        items = a;
        nextFirst = a.length - 1;
        nextLast = size();
    }

    public void addFirst(T item) {
        if (size() == items.length) {
            resize(items.length * 2);
        }

        if (nextFirst < 0) {
            nextFirst = items.length - 1;
        }

        items[nextFirst] = item;
        nextFirst--;
        size++;
    }

    public void addLast(T item) {
        if (size() == items.length) {
            resize(items.length * 2);
        }

        if (nextLast >= items.length) {
            nextLast = 0;
        }

        items[nextLast] = item;
        nextLast++;
        size++;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int k = nextFirst + 1;
        for (int i = 0; i < size(); i++) {
            if (k == items.length) {
                k = 0;
            }
            System.out.print(items[k] + " ");
            k++;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        if (nextFirst + 1 >= items.length) {
            nextFirst = -1;
        }

        T item = items[nextFirst + 1];
        items[nextFirst + 1] = null;
        nextFirst++;
        size--;

        return item;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        if (nextLast - 1 < 0) {
            nextLast = items.length;
        }

        T item = items[nextLast - 1];
        items[nextLast - 1] = null;
        nextLast--;
        size--;

        return item;
    }

    public T get(int index) {
        int i = (nextFirst + index + 1) % items.length;
        return items[i];
    }
}
