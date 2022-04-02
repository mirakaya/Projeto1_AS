package HCP.Monitors;

/**
 * Implementation of a simple non synchronized queue
 * @param <T>
 */
public class BoundedQueue<T> {
    private final int size;
    private int count;
    private int getIndex;
    private int putIndex;

    private final Object[] storage;

    public BoundedQueue(int size) {
        this.size = size;
        this.count = 0;
        this.getIndex = 0;
        this.putIndex = 0;
        this.storage = new Object[size];
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public void enqueue(T obj) {
        if (this.count == this.size) throw new IllegalStateException("The queue of full");

        this.storage[this.putIndex] = obj;
        this.count++;
        this.putIndex = (this.putIndex + 1) % this.size;
    }

    public T dequeue() {
        if (this.isEmpty()) throw new IllegalStateException("The queue is empty");

        T obj = (T) this.storage[this.getIndex];
        this.count--;
        this.getIndex = (this.getIndex + 1) % this.size;

        return obj;
    }
}
