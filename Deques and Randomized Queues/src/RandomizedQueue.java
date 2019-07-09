import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author: salimt
 */

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] data;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.data = (Item[]) new Object[2];
        this.size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        resize(size*2);
        data[size()] = item;
        size++;
    }

    private void resize(int capacity) {
        if (size() == data.length) {
            data = Arrays.copyOf(data, capacity);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int rand = StdRandom.uniform(size());
        Item temp = data[rand];

        System.arraycopy(data, rand + 1, data, rand, data.length - 1 - rand);
        size--;
        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return data[StdRandom.uniform(size())];
    }

    // return an independent iterator over items in random order
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
            return dequeue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
