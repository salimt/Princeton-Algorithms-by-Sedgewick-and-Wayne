import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author: salimt
 */

public class Deque<Item> implements Iterable<Item> {

    private final Node head, tail;
    private int size;

    public Deque() {
        this.size = 0;
        this.head = new Node(null);
        this.tail = new Node(null);
        head.next = tail;
        tail.prev = head;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        size++;
        Node temp = new Node(item);
        temp.next = head.next;
        temp.prev = head;
        head.next.prev = temp;
        head.next = temp;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        size++;
        Node temp = new Node(item);
        temp.prev = tail.prev;
        temp.next = tail;
        tail.prev.next = temp;
        tail.prev = temp;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        size--;
        Node temp = head.next;
        temp.next.prev = head;
        head.next = temp.next;
        return temp.getItem();
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        size--;
        Node temp = tail.prev;
        temp.prev.next = tail;
        tail.prev = temp.prev;
        return temp.getItem();

    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ItemIterator();
    }

    private class ItemIterator implements Iterator<Item> {

        @Override
        public boolean hasNext() {
            return !isEmpty();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return removeFirst();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node {

        private final Item item;
        private Node next;
        private Node prev;

        public Node(Item item) {
            this.item = item;
        }

        public Item getItem() {
            return item;
        }

    }

}
