import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    
    private Node first;
    private Node last;
    private int size;
    
    private class Node {
        Item item;
        Node next;
        Node prev;
    }
    public Deque() {                           // construct an empty deque
        first = null;
        last = null;
        size = 0;
    }
    public boolean isEmpty() {                 // is the deque empty?
        return first == null;
    }
    public int size() {                        // return the number of items on the deque
        return size;
    }
    public void addFirst(Item item) {         // add the item to the front
        if (item == null) { throw new IllegalArgumentException(); }
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            first.next = null;
            first.prev = null;
            last = first;
            size++;
        }
        else {
            Node temp = new Node();
            temp.item = item;
            temp.next = first;
            temp.prev = null;
            first.prev = temp;
            first = temp;
            size++;
        }
    }
    public void addLast(Item item) {           // add the item to the end
        if (item == null) { throw new IllegalArgumentException(); }
        if (isEmpty()) {
            last = new Node();
            last.item = item;
            last.next = null;
            last.prev = null;
            first = last;
            size++;
        }
        else {
            Node temp = new Node();
            temp.item = item;
            temp.next = null;
            temp.prev = last;
            last.next = temp;
            last = temp;
            size++;
        }
    }
    public Item removeFirst() {               // remove and return the item from the front
        if (isEmpty()) { throw new NoSuchElementException(); }
        if (last == first) { 
            Item temp = first.item;
            last = null;
            first = null;
            return temp;
        } 
        Item temp = first.item;
        first = first.next;
        first.prev = null;
        size--;
        return temp;
    }
    public Item removeLast() {                 // remove and return the item from the end
        if (isEmpty()) { throw new NoSuchElementException(); }
        if (last == first) { 
            Item temp = last.item;
            last = null;
            first = null;
            return temp;
        } 
        Item temp = last.item;
        last = last.prev;
        last.next = null;
        size--;
        return temp;
    }
    public Iterator<Item> iterator() {        // return an iterator over items in order from front to end
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException(); }
            Item temp = current.item;
            current = current.next;
            return temp;
        }
        public void remove() { throw new UnsupportedOperationException(); }
    }
    public static void main(String[] args) {  // unit testing (optional)
        Deque<Integer> dq = new Deque<Integer>();
        dq.addFirst(1);
        dq.addLast(2);
        dq.addFirst(3);
        System.out.println(dq.removeLast());
        System.out.println(dq.removeLast());
        System.out.println(dq.removeLast());
        System.out.println(dq.removeLast());
    }
}
