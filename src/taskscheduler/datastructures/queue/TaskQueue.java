package taskscheduler.datastructures.queue;

import taskscheduler.Task;

/**
 * A FIFO (First-In-First-Out) queue used to schedule and process tasks in order.
 * Tasks are enqueued (added to the back) and dequeued (removed from the front),
 * ensuring tasks are executed in the order they were scheduled.
 */
public class TaskQueue {

    private static class QNode {
        Task task;
        QNode next;

        QNode(Task task) {
            this.task = task;
        }
    }

    private QNode front;
    private QNode rear;
    private int size;

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return front == null;
    }

    /** Add a task to the back of the execution queue. */
    public void enqueue(Task task) {
        QNode node = new QNode(task);
        if (rear != null) {
            rear.next = node;
        }
        rear = node;
        if (front == null) {
            front = node;
        }
        size++;
    }

    /** Remove and return the task at the front of the queue (next to be executed). */
    public Task dequeue() {
        if (isEmpty()) {
            return null;
        }
        Task task = front.task;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return task;
    }

    /** Return the task at the front without removing it. */
    public Task peek() {
        if (isEmpty()) {
            return null;
        }
        return front.task;
    }

    /** Display all tasks currently waiting in the execution queue. */
    public void display() {
        if (isEmpty()) {
            System.out.println("Execution queue is empty.");
            return;
        }
        String format = "%-5s | %-20s | %-10s | %-15s%n";
        System.out.printf(format, "ID", "Task Name", "Priority", "Deadline");
        System.out.println("------------------------------------------------------------");
        QNode temp = front;
        while (temp != null) {
            Task task = temp.task;
            System.out.printf(format,
                    task.getId(),
                    task.getTaskName(),
                    task.getPriority(),
                    task.getFormattedDeadline());
            temp = temp.next;
        }
    }
}
