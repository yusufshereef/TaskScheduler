package taskscheduler;

import taskscheduler.datastructures.hashtable.HashTable;
import taskscheduler.datastructures.linkedlist.DoublyLinkedList;
import taskscheduler.datastructures.sorting.SortByDeadlineUsingMergeSort;
import taskscheduler.datastructures.sorting.SortByPriorityUsingQuickSort;

public class TaskManager {
    private HashTable hashTable = new HashTable();
    DoublyLinkedList dll = new DoublyLinkedList();
    public void createTask(String taskName, String deadline, int priority){
        if(hashTable.get(taskName)==null){
            Task task = new Task(taskName, deadline, priority);
            DoublyLinkedList.DLLNode dllNode = dll.addNode(task);
            System.out.println();
            System.out.println("successfully added task in dll");
            hashTable.put(task, dllNode);
            System.out.println();
        }else{
            System.out.println();
            System.out.println("task already exists");
            System.out.println();
        }
    }

    public void deleteTask(String taskName){
        if(hashTable.get(taskName)!=null){
            DoublyLinkedList.DLLNode dllNode = hashTable.getDLLNode(taskName);
            System.out.println();
            System.out.println("get dll node from hash table success");
            dll.deleteNode(dllNode);
            System.out.println("delete from dll success");
            hashTable.delete(taskName);
            System.out.println("delete from hash table success");
            System.out.println("Task deleted");
            System.out.println();
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
        System.out.println();
        System.out.println("Task successfully updated");
        System.out.println();
    }

    public void updateTaskPriority(String taskName, int priority){
        hashTable.updateTaskPriority(taskName, priority);
        System.out.println();
        System.out.println("Task successfully updated");
        System.out.println();
    }

    public void searchTask(String taskName){
        Task task = hashTable.get(taskName);
        if(task!=null){
            System.out.println();
            String format = "%-5s | %-20s | %-10s | %-15s%n";
            System.out.println("------------------------------------------------------------");
            System.out.printf(format, "ID", "Task Name", "Priority", "Deadline");
            System.out.printf(format,
                    task.getId(),
                    task.getTaskName(),
                    task.getPriority(),
                    task.getFormattedDeadline());
            System.out.println();
        }else{
            System.out.println();
            System.out.println("NO SUCH TASK");
            System.out.println();
        }
    }

    public void sortByDeadline(){
        Task[] tasks = dll.getTaskArray();
        SortByDeadlineUsingMergeSort s = new SortByDeadlineUsingMergeSort(tasks);
        s.displaySorted();
    }

    public void sortByPriority(){
        Task[] tasks = dll.getTaskArray();
        SortByPriorityUsingQuickSort s = new SortByPriorityUsingQuickSort(tasks);
        s.displaySorted();
    }

}
