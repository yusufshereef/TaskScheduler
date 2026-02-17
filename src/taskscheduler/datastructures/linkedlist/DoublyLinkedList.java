package taskscheduler.datastructures.linkedlist;

import taskscheduler.Task;

public class DoublyLinkedList {
    public class DLLNode{
        private DLLNode prev;
        Task task;
        private DLLNode next;

        DLLNode(Task task){
            this.task = task;
        }
    }
    private DLLNode head;
    private int size;

    public int getSize(){
        return this.size;
    }

    public DLLNode addNode(Task task){
        DLLNode node = new DLLNode(task);
        if(head != null){
            head.prev = node;
            node.next = head;
        }
        head = node;
        size++;
        return node;
    }

    public void deleteNode(DLLNode node){
        if(node == null) return;
        if(node == head){
            head = head.next;
            if(head!=null){
                head.prev = null;
            }
        }
        else if(node.next == null){
            node.prev.next = null;
            node.prev = null;
        }else{
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
            node.prev = null;
        }
        size--;
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

    public void display(){
        DLLNode temp = head;
        if(head == null){
            System.out.println("NO TASKS FOUND");
            return;
        }
        int count = 1;
        System.out.println("------------Task Name----------------------------"+"||-----------------------------------priority--------------"+"||------------------deadline--------------");
        while(temp!=null){
            Task task = temp.task;
            System.out.println(count++ + ".        "+ task.getTaskName() + "                                           "+task.getPriority()+"                                   "+task.getFormattedDeadline());
            temp=temp.next;
        }
        System.out.println();
    }
}
