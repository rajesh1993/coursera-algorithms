import edu.princeton.cs.algs4.StdRandom;
import java.util.*;
public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Node first;
    private Node last;
    private int size;
    
    private class Node {
        Item item;
        Node next;
        Node prev;
    }
    public RandomizedQueue() {                 // construct an empty randomized queue
        first = null;
        last = null;
        size = 0;
    }
    public boolean isEmpty() {                // is the randomized queue empty?
        return first == null;
    }
    public int size() {                       // return the number of items on the randomized queue
        return size;
    }
    public void enqueue(Item item) {          // add the item
        if (item == null) { throw new IllegalArgumentException(); }
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            first.next = null;
            first.prev = null;
        }
        else {
            Node temp = new Node();
            temp.item = item;
            temp.next = first;
            temp.prev = null;
            first = temp;
        }
        size++;
    }
    public Item dequeue() {                   // remove and return a random item
        if (isEmpty()) { throw new NoSuchElementException(); }
        if (first == last) {
            Item temp = first.item;
            first = null;
            last = null;
            return temp;
        }
        int randNumber = StdRandom.uniform(size);
        if (randNumber > size / 2){
            int idx = size;
            Node current = last;
            while (idx > randNumber) {
                current = current.prev;
                idx--;
            }
        }
        else {
            int idx = size;
            Node current = first;
            while (idx < randNumber) {
                current = current.next;
                idx++;
            }
        }
        int item = current.item;
        current.prev = 
        return current.item;
    }
    public Item sample()                     // return a random item (but do not remove it)
    public Iterator<Item> iterator() {        // return an independent iterator over items in random order
        return new RandomizeQueueIterator();
    }
    private RandomizeQueueIterator<Item> {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public Item next() {
            Item temp = current.item;
            current = current.next;
            return temp;
        }
        public void remove() { throw new UnsupportedOperationException(); }
    }
    public static void main(String[] args)   // unit testing (optional)
}