package taskscheduler.datastructures.linkedlist;

import taskscheduler.Task;

public class DoublyLinkedList {
    class DLLNode{
        DLLNode prev;
        Task task;
        DLLNode next;

        DLLNode(Task task){
            this.task = task;
        }
    }
}
