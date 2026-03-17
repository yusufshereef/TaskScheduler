package taskscheduler;

import taskscheduler.datastructures.hashtable.HashTable;
import taskscheduler.datastructures.linkedlist.DoublyLinkedList;
import taskscheduler.datastructures.queue.TaskQueue;
import taskscheduler.datastructures.sorting.SortByDeadlineUsingMergeSort;
import taskscheduler.datastructures.sorting.SortByPriorityUsingQuickSort;
import taskscheduler.datastructures.stack.TaskStack;
import taskscheduler.filehandling.FileStorage;

public class TaskManager {
    private HashTable hashTable = new HashTable();
    private DoublyLinkedList dll = new DoublyLinkedList();
    private FileStorage fileStorage = new FileStorage();
    private TaskQueue taskQueue = new TaskQueue();
    private TaskStack completedStack = new TaskStack();

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

    /** Schedule an existing task for execution by adding it to the FIFO queue. */
    public void scheduleTask(String taskName) {
        Task task = hashTable.get(taskName);
        if (task == null) {
            System.out.println();
            System.out.println("No such task found.");
            System.out.println();
            return;
        }
        if (task.isCompleted()) {
            System.out.println();
            System.out.println("Task is already completed and cannot be scheduled.");
            System.out.println();
            return;
        }
        taskQueue.enqueue(task);
        System.out.println();
        System.out.println("Task \"" + task.getTaskName() + "\" added to execution queue. Queue size: " + taskQueue.getSize());
        System.out.println();
    }

    /**
     * Execute the next task from the queue (FIFO order).
     * The task is marked as completed and pushed onto the completed-tasks stack.
     */
    public void executeNextTask() {
        Task task = taskQueue.dequeue();
        if (task == null) {
            System.out.println();
            System.out.println("Execution queue is empty. No tasks to execute.");
            System.out.println();
            return;
        }
        task.setCompleted(true);
        completedStack.push(task);
        System.out.println();
        System.out.println("Executed task: \"" + task.getTaskName() + "\" (ID: " + task.getId() + ")");
        System.out.println("Remaining tasks in queue: " + taskQueue.getSize());
        System.out.println();
    }

    /** Display all tasks currently waiting in the execution queue. */
    public void displayQueue() {
        System.out.println();
        System.out.println("--- Execution Queue (FIFO) ---");
        taskQueue.display();
        System.out.println();
    }

    /** Mark an existing task as completed and push it onto the completed-tasks stack. */
    public void markTaskCompleted(String taskName) {
        Task task = hashTable.get(taskName);
        if (task == null) {
            System.out.println();
            System.out.println("No such task found.");
            System.out.println();
            return;
        }
        if (task.isCompleted()) {
            System.out.println();
            System.out.println("Task is already marked as completed.");
            System.out.println();
            return;
        }
        task.setCompleted(true);
        completedStack.push(task);
        System.out.println();
        System.out.println("Task \"" + task.getTaskName() + "\" marked as completed.");
        System.out.println();
    }

    /**
     * Undo the last task completion by popping from the completed-tasks stack
     * and restoring the task to an active (not completed) state.
     */
    public void undoLastCompletion() {
        Task task = completedStack.pop();
        if (task == null) {
            System.out.println();
            System.out.println("Nothing to undo. No completed tasks in history.");
            System.out.println();
            return;
        }
        task.setCompleted(false);
        System.out.println();
        System.out.println("Undo successful. Task \"" + task.getTaskName() + "\" is now active again.");
        System.out.println();
    }

    /** Display all recently completed tasks (most recent first). */
    public void displayCompletedTasks() {
        System.out.println();
        System.out.println("--- Completed Tasks History (most recent first) ---");
        completedStack.display();
        System.out.println();
    }

}
