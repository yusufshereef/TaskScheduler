package taskscheduler.datastructures.linkedlist;
import taskscheduler.Task;

public class DoublyTaskList {
    // Node class
    public class Node {
        Task task;
        Node next;
        Node prev;

        Node(Task task) {
            this.task = task;
            this.next = null;
            this.prev = null;
        }
    }
    private Node head;
    private Node tail;
    public DoublyTaskList() {
        head = null;
        tail = null;
    }
    public Node getHead() {
        return head;
    }

    public void addTask(Task task) {

        Node newNode = new Node(task);
        if (head == null) {
            head = newNode;
            tail = newNode;
        }
        else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }


    public void displayForward() {
        Node temp = head;

        while (temp != null) {
            System.out.println(
                    "ID: " + temp.task.getId() + " | Name: " + temp.task.getTaskName() + " | Priority: " + temp.task.getPriority() + " | Status: " + (temp.task.getStatus() == 0 ? "Pending" : "Completed")
            );
            temp = temp.next;
        }
    }

    public void displayBackward() {
        Node temp = tail;

        while (temp != null) {
            System.out.println(
                    "ID: " + temp.task.getId() +
                            " | Name: " + temp.task.getTaskName()
            );
            temp = temp.prev;
        }
    }

    public Task searchById(int id) {
        Node temp = head;

        while (temp != null) {
            if (temp.task.getId() == id) {
                return temp.task;
            }
            temp = temp.next;
        }

        return null;
    }
    public int getSize() {

        int count = 0;
        Node temp = head;

        while (temp != null) {
            count++;
            temp = temp.next;
        }

        return count;
    }
    public Task getTaskAt(int index) {
        Node temp = head;
        int i = 0;
        while (temp != null) {
            if (i == index) {
                return temp.task;
            }
            i++;
            temp = temp.next;
        }
        return null;
    }
    public void deleteTask(int id) {
        Node temp = head;
        while (temp != null) {
            if (temp.task.getId() == id) {
                if (temp == head) {
                    head = temp.next;
                    if (head != null)
                        head.prev = null;
                    else
                        tail = null;
                }
                else if (temp == tail) {
                    tail = temp.prev;
                    tail.next = null;
                }
                else {
                    temp.prev.next = temp.next;
                    temp.next.prev = temp.prev;
                }

                System.out.println("Task deleted.");
                return;
            }

            temp = temp.next;
        }

        System.out.println("Task not found.");
    }
}
