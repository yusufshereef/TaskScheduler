package taskscheduler;

import taskscheduler.datastructures.hashtable.HashTable;
import taskscheduler.datastructures.linkedlist.DoublyLinkedList;

public class TaskManager {
    private HashTable hashTable = new HashTable();
    DoublyLinkedList dll = new DoublyLinkedList();
    public void createTask(String taskName, String deadline, int priority){
        if(hashTable.get(taskName)==null){
            Task task = new Task(taskName, deadline, priority);
            DoublyLinkedList.DLLNode dllNode = dll.addNode(task);
            hashTable.put(task, dllNode);
        }else{
            System.out.println();
            System.out.println("task already exists");
            System.out.println();
        }
    }

    public void deleteTask(String taskName){
        if(hashTable.get(taskName)!=null){
            DoublyLinkedList.DLLNode dllNode = hashTable.getDLLNode(taskName);
            System.out.println("get dll node from hash table success");
            dll.deleteNode(dllNode);
            System.out.println("delete from dll success");
            hashTable.delete(taskName);
            System.out.println("delete from hash table success");
            System.out.println("Task deleted");
        }else{
            System.out.println();
            System.out.println("No such Task");
            System.out.println();
        }
    }
//
    public void displayTasks(){
        System.out.println();
        dll.display();
        System.out.println();
    }

    public void updateTaskDeadline(String taskName, String deadline){
        hashTable.updateTaskDeadline(taskName, deadline);
        System.out.println("Task successfully updated");
        System.out.println();
    }

    public void updateTaskPriority(String taskName, int priority){
        hashTable.updateTaskPriority(taskName, priority);
        System.out.println("Task successfully updated");
        System.out.println();
    }

    public void searchTask(String taskName){
        Task task = hashTable.get(taskName);
        if(task!=null){
            System.out.println("------------Task Name---------------"+"||----------------------priority--------------"+"||------------------deadline--------------");
            System.out.println(task.getId()+ ".        "+ task.getTaskName() + "                                 "+task.getPriority()+"                                   "+task.getDeadline());
        }else{
            System.out.println();
            System.out.println("NO SUCH TASK");
            System.out.println();
        }
    }

}
