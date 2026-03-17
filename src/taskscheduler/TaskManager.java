package taskscheduler;

import taskscheduler.datastructures.hashtable.HashTable;
import taskscheduler.datastructures.linkedlist.DoublyLinkedList;
import taskscheduler.datastructures.sorting.SortByDeadlineUsingMergeSort;
import taskscheduler.datastructures.sorting.SortByPriorityUsingQuickSort;
import taskscheduler.datastructures.stack.Stack;
import taskscheduler.filehandling.FileStorage;

public class TaskManager {
    private HashTable hashTable = new HashTable();
    private DoublyLinkedList dll = new DoublyLinkedList();
    private FileStorage fileStorage = new FileStorage();
    private Stack<Action> undoStack = new Stack<>();

    public void initalize(){
        fileStorage.loadDLLFromFile(dll, hashTable);
    }

    public void save(){
        fileStorage.writeToFile(dll);
    }

    public void createTask(String taskName, String deadline, int priority){
        if(hashTable.get(taskName)==null){
            Task task = new Task(taskName, deadline, priority);
            DoublyLinkedList.DLLNode dllNode = dll.addNode(task);
            System.out.println();
            System.out.println("successfully added task in dll");
            hashTable.put(task, dllNode);
            undoStack.push(new Action(Action.Type.CREATE, task));
            System.out.println();
        }else{
            System.out.println();
            System.out.println("task already exists");
            System.out.println();
        }
    }

    public void deleteTask(String taskName){
        if(hashTable.get(taskName)!=null){
            Task task = hashTable.get(taskName);
            DoublyLinkedList.DLLNode dllNode = hashTable.getDLLNode(taskName);
            System.out.println();
            System.out.println("get dll node from hash table success");
            dll.deleteNode(dllNode);
            System.out.println("delete from dll success");
            hashTable.delete(taskName);
            System.out.println("delete from hash table success");
            System.out.println("Task deleted");
            undoStack.push(new Action(Action.Type.DELETE, task));
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
        Task task = hashTable.get(taskName.trim().toLowerCase());
        if(task != null){
            String oldDeadline = task.getFormattedDeadline();
            hashTable.updateTaskDeadline(taskName, deadline);
            undoStack.push(new Action(Action.Type.UPDATE_DEADLINE, taskName.trim().toLowerCase(), oldDeadline));
            System.out.println();
            System.out.println("Task successfully updated");
            System.out.println();
        }
    }

    public void updateTaskPriority(String taskName, int priority){
        Task task = hashTable.get(taskName.trim().toLowerCase());
        if(task != null){
            String oldPriority = String.valueOf(task.getPriority());
            hashTable.updateTaskPriority(taskName, priority);
            undoStack.push(new Action(Action.Type.UPDATE_PRIORITY, taskName.trim().toLowerCase(), oldPriority));
            System.out.println();
            System.out.println("Task successfully updated");
            System.out.println();
        }
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

    public void undo(){
        if(undoStack.isEmpty()){
            System.out.println();
            System.out.println("Nothing to undo.");
            System.out.println();
            return;
        }
        Action action = undoStack.pop();
        switch(action.getType()){
            case CREATE:
                Task createdTask = action.getTask();
                DoublyLinkedList.DLLNode node = hashTable.getDLLNode(createdTask.getTaskName());
                dll.deleteNode(node);
                hashTable.delete(createdTask.getTaskName());
                System.out.println();
                System.out.println("Undo: removed task '" + createdTask.getTaskName() + "'");
                System.out.println();
                break;
            case DELETE:
                Task deletedTask = action.getTask();
                if(hashTable.get(deletedTask.getTaskName()) == null){
                    DoublyLinkedList.DLLNode dllNode = dll.addNode(deletedTask);
                    hashTable.put(deletedTask, dllNode);
                    System.out.println();
                    System.out.println("Undo: restored task '" + deletedTask.getTaskName() + "'");
                    System.out.println();
                }
                break;
            case UPDATE_DEADLINE:
                hashTable.updateTaskDeadline(action.getTaskName(), action.getOldValue());
                System.out.println();
                System.out.println("Undo: restored deadline for task '" + action.getTaskName() + "'");
                System.out.println();
                break;
            case UPDATE_PRIORITY:
                hashTable.updateTaskPriority(action.getTaskName(), Integer.parseInt(action.getOldValue()));
                System.out.println();
                System.out.println("Undo: restored priority for task '" + action.getTaskName() + "'");
                System.out.println();
                break;
        }
    }

}
