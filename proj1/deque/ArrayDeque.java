package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
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

    @Override
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

    @Override
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

    @Override
    public int size() {
        return size;
    }

    @Override
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

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        if (items.length >= 16 && size() * 4 <= items.length) {
            resize(items.length / 4);
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

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        if (items.length >= 16 && size() * 4 <= items.length) {
            resize(items.length / 4);
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

    @Override
    public T get(int index) {
        int i = (nextFirst + index + 1) % items.length;
        return items[i];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Deque)) {
            return false;
        }

        Deque<T> other = (Deque<T>) o;
        if (size() != other.size()) {
            return false;
        }

        for (int i = 0; i < size(); i++) {
            if (!get(i).equals(other.get(i))) {
                return false;
            }
        }

        return true;
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator<>();
    }

    private class ArrayDequeIterator<T> implements Iterator<T> {
        private int pos;

        ArrayDequeIterator() {
            pos = 0;
        }

        public boolean hasNext() {
            return pos != size();
        }

        public T next() {
            if (isEmpty()) {
                return null;
            }

            T item = (T) get(pos);
            pos++;
            return item;
        }
    }
}
