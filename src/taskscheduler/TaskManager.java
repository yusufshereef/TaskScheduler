package taskscheduler;

import taskscheduler.datastructures.hashtable.HashTable;
import taskscheduler.datastructures.linkedlist.DoublyLinkedList;
import taskscheduler.datastructures.sorting.SortByDeadlineUsingMergeSort;
import taskscheduler.datastructures.sorting.SortByPriorityUsingQuickSort;
import taskscheduler.filehandling.FileStorage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private HashTable hashTable = new HashTable();
    private DoublyLinkedList dll = new DoublyLinkedList();
    private FileStorage fileStorage = new FileStorage();

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

    public void displayTasksByDate(){
        Task[] tasks = dll.getTaskArray();
        if(tasks.length == 0){
            System.out.println();
            System.out.println("NO TASKS FOUND");
            System.out.println();
            return;
        }

        // Sort tasks by deadline using existing merge sort (sorts in-place via constructor)
        new SortByDeadlineUsingMergeSort(tasks);

        // Group tasks by date in insertion order
        Map<LocalDate, List<Task>> tasksByDate = new LinkedHashMap<>();
        for(Task task : tasks){
            LocalDate date = task.getDeadline().toLocalDate();
            tasksByDate.computeIfAbsent(date, d -> new ArrayList<>()).add(task);
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        System.out.println();
        System.out.println("=== Task Schedule by Date ===");
        System.out.println();

        LocalDate previousDate = null;
        for(Map.Entry<LocalDate, List<Task>> entry : tasksByDate.entrySet()){
            LocalDate date = entry.getKey();
            List<Task> dayTasks = entry.getValue();

            if(previousDate == null){
                System.out.println("On " + date.format(dateFormatter) + ", you have:");
            } else {
                long daysBetween = previousDate.until(date, java.time.temporal.ChronoUnit.DAYS);
                if(daysBetween == 1){
                    System.out.println("Next day (" + date.format(dateFormatter) + "), you have:");
                } else {
                    System.out.println("On " + date.format(dateFormatter) + ", you have:");
                }
            }

            String priorityLabel;
            for(Task task : dayTasks){
                priorityLabel = (task.getPriority() == 1) ? "HIGH" : (task.getPriority() == 2) ? "LOW" : "UNKNOWN";
                System.out.println("  - " + task.getTaskName() +
                        " (Priority: " + priorityLabel +
                        ", Time: " + task.getDeadline().format(timeFormatter) + ")");
            }
            System.out.println();
            previousDate = date;
        }
    }

}
