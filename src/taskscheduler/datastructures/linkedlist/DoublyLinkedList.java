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

    public void display(){
        DLLNode temp = head;
        if(head == null){
            System.out.println("NO TASKS FOUND");
            return;
        }
        int count = 1;
        System.out.println("------------Task Name---------------"+"||----------------------priority--------------"+"||------------------deadline--------------");
        while(temp!=null){
            Task task = temp.task;
            System.out.println(count++ + ".        "+ task.getTaskName() + "                                 "+task.getPriority()+"                                   "+task.getDeadline());
            temp=temp.next;
        }
        System.out.println();
    }
}
