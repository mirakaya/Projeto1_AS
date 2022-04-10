package HCP.Utils;

public class UnboundedQueue<T> {

    private int count = 0;
    private QueueNode first = null;
    private QueueNode last = null;

    public boolean isEmpty() {
        return this.count == 0;
    }

    public void enqueue(T value) {
        QueueNode newNode = new QueueNode(value);

        if (this.isEmpty()) {
            this.first = newNode;
        } else {
            this.last.setNext(newNode);
        }

        this.last = newNode;
        this.count++;
    }

    public T dequeue() {
        // If the queue is empty return null
        if (this.isEmpty()) return null;

        QueueNode firstNode = this.first;
        this.first = firstNode.getNext();
        this.count--;

        // If the queue is empty now get rid of the lingering last node
        if (this.isEmpty()) this.last = null;

        return firstNode.getValue();
    }

    private class QueueNode {
        private final T value;

        private QueueNode next = null;

        public QueueNode(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public QueueNode getNext() {
            return next;
        }

        public void setNext(QueueNode next) {
            this.next = next;
        }
    }
}
