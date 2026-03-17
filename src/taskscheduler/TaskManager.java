package taskscheduler;

import taskscheduler.datastructures.hashtable.HashTable;
import taskscheduler.datastructures.linkedlist.DoublyLinkedList;
import taskscheduler.datastructures.sorting.SortByDeadlineUsingMergeSort;
import taskscheduler.datastructures.sorting.SortByPriorityUsingQuickSort;
import taskscheduler.datastructures.stacks.Stack_Completion;
import taskscheduler.filehandling.FileStorage;

public class TaskManager {

    private HashTable hashTable = new HashTable();
    private DoublyLinkedList dll = new DoublyLinkedList();
    private FileStorage fileStorage = new FileStorage();

    Stack_Completion undoStack = new Stack_Completion();
    Stack_Completion redoStack = new Stack_Completion();

    // =========================
    // FILE INITIALIZATION
    // =========================

    public void initalize(){
        fileStorage.loadDLLFromFile(dll, hashTable);
    }

    public void save(){
        fileStorage.writeToFile(dll);
    }

    // =========================
    // FOCUS MODE
    // =========================

    public void focusMode(){

        if(dll.getSize() == 0){
            System.out.println("No tasks available.");
            return;
        }

        Task[] tasks = dll.getTaskArray();

        // Step 1 → sort by priority
        new SortByPriorityUsingQuickSort(tasks);

        // Step 2 → sort by deadline
        new SortByDeadlineUsingMergeSort(tasks);

        System.out.println("\n===== FOCUS MODE (Top 3 Tasks) =====");

        int limit = Math.min(3, tasks.length);

        for(int i = 0; i < limit; i++){
            System.out.println(
                    "ID: " + tasks[i].getId() +
                            " | Name: " + tasks[i].getTaskName() +
                            " | Priority: " + tasks[i].getPriority()
            );
        }
    }

    // =========================
    // CREATE TASK
    // =========================

    public void createTask(String taskName, String deadline, int priority){

        if(hashTable.get(taskName) == null){

            Task task = new Task(taskName, deadline, priority);

            DoublyLinkedList.DLLNode node = dll.addNode(task);

            hashTable.put(task, node);

        } else {
            System.out.println("Task already exists");
        }
    }

    // =========================
    // DELETE TASK
    // =========================

    public void deleteTask(String taskName){

        Task task = hashTable.get(taskName);

        if(task != null){

            undoStack.push(task);

            DoublyLinkedList.DLLNode node = hashTable.getDLLNode(taskName);

            dll.deleteNode(node);

            hashTable.delete(taskName);

            System.out.println("Task deleted");

        } else {
            System.out.println("No such Task");
        }
    }

    // =========================
    // DISPLAY TASKS
    // =========================

    public void displayTasks(){
        dll.display();
    }

    public DoublyLinkedList getList(){
        return dll;
    }

    // =========================
    // SORT BY DEADLINE
    // =========================

    public void mergesort(){

        SortByDeadlineUsingMergeSort sorter =
                new SortByDeadlineUsingMergeSort(dll.getTaskArray());

        sorter.displaySorted();
    }

    // =========================
    // SORT BY PRIORITY
    // =========================

    public void quicksort(){

        SortByPriorityUsingQuickSort sorter =
                new SortByPriorityUsingQuickSort(dll.getTaskArray());

        sorter.displaySorted();
    }

    // =========================
    // DELETE BY ID
    // =========================

    public void deleteTaskById(int id){

        for(int i = 0; i < dll.getSize(); i++){

            Task task = dll.getAt(i);

            if(task.getId() == id){

                deleteTask(task.getTaskName());
                return;
            }
        }

        System.out.println("Task ID not found");
    }

    // =========================
    // UNDO DELETE
    // =========================

    public void undoDelete(){

        Task task = undoStack.pop();

        if(task == null){
            System.out.println("Nothing to undo");
            return;
        }

        DoublyLinkedList.DLLNode node = dll.addNode(task);

        hashTable.put(task, node);

        redoStack.push(task);

        System.out.println("Undo successful. Task restored: " + task.getTaskName());
    }

    // =========================
    // REDO DELETE
    // =========================

    public void redoDelete(){

        Task task = redoStack.pop();

        if(task == null){
            System.out.println("Nothing to redo");
            return;
        }

        deleteTask(task.getTaskName());

        System.out.println("Redo successful.");
    }

    // =========================
    // RECENTLY DELETED
    // =========================

    public void showRecentlyDeleted(){
        undoStack.displayRecent();
    }

}