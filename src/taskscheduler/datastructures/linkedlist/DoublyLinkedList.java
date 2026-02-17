package taskscheduler.datastructures.linkedlist;

import taskscheduler.Task;

public class DoublyLinkedList {

    public class DLLNode {
        DLLNode prev;
        DLLNode next;
        Task task;

        DLLNode(Task task) {
            this.task = task;
        }
    }

    private DLLNode head;
    private DLLNode tail;
    private int size;
    public DoublyLinkedList() {
        head = null;
        tail = null;
    }
    public DLLNode addNode(Task task) {
        DLLNode node = new DLLNode(task);

        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }

        size++;
        return node;
    }
    public void deleteNode(DLLNode node) {
        if (node == null) return;

        if (node == head) {
            head = node.next;
            if (head != null) head.prev = null;
            else tail = null;
        }
        else if (node == tail) {
            tail = node.prev;
            tail.next = null;
        }
        else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        size--;
    }

    public int getSize() {
        return size;
    }

    public Task getAt(int index) {
        DLLNode temp = head;
        int i = 0;

        while (temp != null) {
            if (i == index)
                return temp.task;
            temp = temp.next;
            i++;
        }
        return null;
    }
    public Task[] getTaskArray(){
        DLLNode temp = head;
        Task[] tasks = new Task[size];
        int count = 0;
        while(temp!=null){
            Task task = temp.task;
            tasks[count++] = task;
            temp = temp.next;
        }
        return tasks;
    }

//    public Task getTaskAt(int index) {
//        Node temp = head;
//        int i = 0;
//        while (temp != null) {
//            if (i == index) {
//                return temp.task;
//            }
//            i++;
//            temp = temp.next;
//        }
//        return null;
//    }

    public void display() {
        if (head == null) {
            System.out.println("NO TASKS FOUND");
            return;
        }

        DLLNode temp = head;
        int count = 1;

        System.out.println("------------ Task Name ------------ || ------------ Deadline ------------");

        while (temp != null) {
            Task task = temp.task;
            System.out.println(
                    count++ + ". " +
                            task.getTaskName() + "   ||   " +
                            task.getFormattedDeadline()
            );
            temp = temp.next;
        }
        System.out.println();
    }
}
