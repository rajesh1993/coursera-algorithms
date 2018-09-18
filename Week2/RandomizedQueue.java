import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
   
    private Item[] store;
    private int size;
    private int head;
    private int tail;
    
    public RandomizedQueue() {                 // construct an empty randomized queue
        store = null;
        size = 0;
    }
    public boolean isEmpty() {                // is the randomized queue empty?
        return store == null;
    }
    public int size() {                       // return the number of items on the randomized queue
        return size;
    }
    public void enqueue(Item item) {           // add the item
        if (item == null) { throw new IllegalArgumentException(); }
        if (isEmpty()) { 
            store = (Item[]) new Object[2];
            head = 0;
            tail = 0;
        }
        if (size == store.length) { resize(2 * store.length); }
        store[tail++] = item;
        size++;
    }
    public Item dequeue() {                   // remove and return a random item
        if (isEmpty()) { throw new NoSuchElementException(); }
        int randNumber = StdRandom.uniform(head, tail);
        Item temp = store[randNumber];
        store[randNumber] = store[tail - 1];
        store[tail - 1] = null;
        tail--;
        size--;
        if (size == store.length / 4) {
            resize(store.length / 2);
        }
        if (size == 0) {
            store = null;
        }
        return temp;
    }
    private void resize(int capacity) {
        Item [] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = store[head + i];
        }
        store = copy;
    }
    public Item sample() {                     // return a random item (but do not remove it)
        if (isEmpty()) { throw new NoSuchElementException(); }
        int randNumber = StdRandom.uniform(head, tail);
        return store[randNumber];
    }
    public Iterator<Item> iterator() {        // return an independent iterator over items in random order
        return new RandomizedQueueIterator();
    }
    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] items;
        private int idx;
        public RandomizedQueueIterator() {
            items = (Item[]) new Object[size];
            for (int i = 0; i < size; i++)
                items[i] = store[head + i];
            StdRandom.shuffle(items);
            idx = size;
        }
        public boolean hasNext() { return idx != 0; }
        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException(); }
            return items[--idx];
        }
        public void remove() { throw new UnsupportedOperationException(); }
    }

    public static void main(String[] args) {   // unit testing (optional)
       
    }
}












