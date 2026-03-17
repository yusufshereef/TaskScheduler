package taskscheduler.datastructures.stack;

import taskscheduler.Task;

/**
 * A LIFO (Last-In-First-Out) stack used to track recently completed tasks.
 * Supports undoing the last task completion by popping the task off the stack
 * and restoring it to an active state.
 */
public class TaskStack {

    private static class SNode {
        Task task;
        SNode next;

        SNode(Task task) {
            this.task = task;
        }
    }

    private SNode top;
    private int size;

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return top == null;
    }

    /** Push a completed task onto the stack. */
    public void push(Task task) {
        SNode node = new SNode(task);
        node.next = top;
        top = node;
        size++;
    }

    /** Pop and return the most recently completed task (for undo). */
    public Task pop() {
        if (isEmpty()) {
            return null;
        }
        Task task = top.task;
        top = top.next;
        size--;
        return task;
    }

    /** Return the most recently completed task without removing it. */
    public Task peek() {
        if (isEmpty()) {
            return null;
        }
        return top.task;
    }

    /** Display all recently completed tasks (top of stack first). */
    public void display() {
        if (isEmpty()) {
            System.out.println("No completed tasks in history.");
            return;
        }
        String format = "%-5s | %-20s | %-10s | %-15s%n";
        System.out.printf(format, "ID", "Task Name", "Priority", "Deadline");
        System.out.println("------------------------------------------------------------");
        SNode temp = top;
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
