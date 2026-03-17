
package taskscheduler.datastructures.stacks;

import taskscheduler.Task;

public class Stack_Completion {
    public Stack_Completion() {
        top = null;
    }
    // Node class for stack
    private class StackNode {
        Task task;
        int previousStatus;
        StackNode next;

        StackNode(Task task, int previousStatus) {
            this.task = task;
            this.previousStatus = previousStatus;
            this.next = null;
        }
    }

    private StackNode top;


    // Push when task is marked completed
    public void push(Task task) {
        if (task == null) return;

        StackNode t = new StackNode(task, task.getStatus());
        t.next = top;
        top = t;
    }

    // Pop when undo is called
    public void undo() {
        if (top == null) {
            System.out.println("Nothing to undo.");
            return;
        }

        StackNode temp = top;
        top = top.next;

        // Restore previous status
        temp.task.markPending();

        System.out.println("Undo successful. Task restored:");
        System.out.println("Task ID: " + temp.task.getId() + " | Name: " + temp.task.getTaskName());
    }

    // Display recently completed tasks
    public void displayRecent() {
        if (top == null) {
            System.out.println("No recently completed tasks.");
            return;
        }

        StackNode temp = top;

        System.out.println("Recently Completed Tasks (Most Recent First):");
        while (temp != null) {
            System.out.println("Task ID: " + temp.task.getId() + " | Name: " + temp.task.getTaskName());
            temp = temp.next;
        }
    }

    public boolean isEmpty() {
        return top == null;
    }

    public Task pop() {
        if (top == null) return null;
        StackNode temp = top;
        top = top.next;
        return temp.task;
    }

}
