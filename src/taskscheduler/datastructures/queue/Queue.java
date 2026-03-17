package taskscheduler.datastructures.queue;

public class Queue<T> {
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> front;
    private Node<T> rear;
    private int size;

    public void enqueue(T item) {
        Node<T> node = new Node<>(item);
        if (rear != null) {
            rear.next = node;
        }
        rear = node;
        if (front == null) {
            front = node;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) return null;
        T item = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return item;
    }

    public T peek() {
        if (isEmpty()) return null;
        return front.data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    public boolean contains(T item) {
        Node<T> current = front;
        while (current != null) {
            if (current.data == item) return true;
            current = current.next;
        }
        return false;
    }

    public Object[] toArray() {
        Object[] arr = new Object[size];
        Node<T> current = front;
        int i = 0;
        while (current != null) {
            arr[i++] = current.data;
            current = current.next;
        }
        return arr;
    }
}
