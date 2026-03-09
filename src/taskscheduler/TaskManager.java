package taskscheduler;

import taskscheduler.datastructures.hashtable.HashTable;
import taskscheduler.datastructures.linkedlist.DoublyLinkedList;
import taskscheduler.datastructures.linkedlist.DoublyTaskList;
import taskscheduler.datastructures.sorting.SortByDeadlineUsingMergeSort;
import taskscheduler.datastructures.sorting.SortByPriorityUsingQuickSort;
import taskscheduler.datastructures.stacks.Stack_Completion;


public class TaskManager {
    private HashTable hashTable = new HashTable();
    DoublyLinkedList dll = new DoublyLinkedList();

    public void createTask(String taskName, String deadline, int priority){
        if(hashTable.get(taskName)==null){
            Task task = new Task(taskName, deadline, priority);
            DoublyLinkedList.DLLNode dllNode = dll.addNode(task);
            hashTable.put(task, dllNode);
        }else{
            System.out.println("task already exists");
        }
    }

    public void deleteTask(String taskName){
        Task task = hashTable.get(taskName);
        if(task != null){
            undoStack.push(task);   // push deleted task into stack
            DoublyLinkedList.DLLNode dllNode = hashTable.getDLLNode(taskName);
            dll.deleteNode(dllNode);
            hashTable.delete(taskName);
            System.out.println("Task deleted");
        }else{
            System.out.println("No such Task");
        }
    }
//
    public void displayTasks(){
        dll.display();
    }

    public DoublyLinkedList getList() {
        return dll;
    }

    public void mergesort(){
        SortByDeadlineUsingMergeSort sorter = new SortByDeadlineUsingMergeSort(this.getList().getTaskArray());
        sorter.displaySorted();
    }

    public void quicksort(){
        SortByPriorityUsingQuickSort sorter = new SortByPriorityUsingQuickSort(getList().getTaskArray());
        sorter.displaySorted();
    }

    //task by id se delete
    public void deleteTaskById(int id) {
        for(int i = 0; i < dll.getSize(); i++) {
            Task task = dll.getAt(i);
            if(task.getId() == id) {
                String taskName = task.getTaskName();
                deleteTask(taskName);
                return;
            }
        }

        System.out.println("Task ID not found");
    }

    //stacks

    Stack_Completion undoStack = new Stack_Completion();
    Stack_Completion redoStack = new Stack_Completion();

    public void undoDelete(){
        Task task = undoStack.pop();
        if(task == null){
            System.out.println("Nothing to undo");
            return;
        }
        DoublyLinkedList.DLLNode node = dll.addNode(task);
        hashTable.put(task,node);
        redoStack.push(task);
        System.out.println("Undo successful. Task restored: " + task.getTaskName());
    }

    public void redoDelete(){
        Task task = redoStack.pop();
        if(task == null){
            System.out.println("Nothing to redo");
            return;
        }
        deleteTask(task.getTaskName());
        System.out.println("Redo successful.");
    }

    public void showRecentlyDeleted(){
        undoStack.displayRecent();
    }


}
