package taskscheduler.datastructures.stack;

public class Stack<T> {
    private Object[] data;
    private int top;
    private static final int INITIAL_CAPACITY = 16;

    public Stack() {
        data = new Object[INITIAL_CAPACITY];
        top = -1;
    }

    public void push(T item) {
        if (top == data.length - 1) {
            Object[] newData = new Object[data.length * 2];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }
        data[++top] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) return null;
        T item = (T) data[top];
        data[top--] = null;
        return item;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) return null;
        return (T) data[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }
}
