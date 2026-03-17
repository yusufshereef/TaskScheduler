package taskscheduler;

import taskscheduler.datastructures.hashtable.HashTable;
import taskscheduler.datastructures.linkedlist.DoublyLinkedList;
import taskscheduler.datastructures.sorting.SortByDeadlineUsingMergeSort;
import taskscheduler.datastructures.sorting.SortByPriorityUsingQuickSort;
import taskscheduler.datastructures.stack.UndoAction;
import taskscheduler.datastructures.stack.UndoStack;
import taskscheduler.filehandling.FileStorage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskManager {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String TASK_ROW_FORMAT = "%-5s | %-20s | %-10s | %-16s | %-16s%n";
    private static final String TABLE_SEPARATOR = "--------------------------------------------------------------------------------";
    private static final String ACTION_CREATE = "CREATE";
    private static final String ACTION_DELETE = "DELETE";
    private static final String ACTION_UPDATE = "UPDATE";

    private HashTable hashTable = new HashTable();
    private DoublyLinkedList dll = new DoublyLinkedList();
    private FileStorage fileStorage = new FileStorage();
    private UndoStack undoStack = new UndoStack();
    private boolean isUndoInProgress = false;

    public void initalize(){
        fileStorage.loadDLLFromFile(dll, hashTable);
    }

    public void save(){
        fileStorage.writeToFile(dll);
    }

    public void createTask(String taskName, String deadline, String taskTime, int priority){
        if(hashTable.get(taskName)==null){
            Task task;
            try{
                task = new Task(taskName, deadline, taskTime, priority);
            }catch (Exception e){
                System.out.println();
                System.out.println("Invalid date/time format. Use yyyy-MM-dd HH:mm");
                System.out.println();
                return;
            }

            if(priority != 1 && priority != 2){
                System.out.println();
                System.out.println("Invalid priority. Use 1 or 2");
                System.out.println();
                return;
            }

            addTaskObject(task);

            if(!isUndoInProgress){
                undoStack.push(new UndoAction(ACTION_CREATE, task.getTaskName(), null, task.copyTask()));
            }

            System.out.println();
        }else{
            System.out.println();
            System.out.println("task already exists");
            System.out.println();
        }
    }

    public void deleteTask(String taskName){
        Task deletedTask = removeTaskObject(taskName);
        if(deletedTask == null){
            System.out.println();
            System.out.println("No such Task");
            System.out.println();
            return;
        }

        if(!isUndoInProgress){
            undoStack.push(new UndoAction(ACTION_DELETE, deletedTask.getTaskName(), deletedTask.copyTask(), null));
        }

        System.out.println();
        System.out.println("Task deleted");
        System.out.println();
    }
//
    public void displayTasks(){
        System.out.println();
        dll.display();
        System.out.println();
    }

    public void updateTaskDeadline(String taskName, String deadline){
        Task task = hashTable.get(taskName);
        if(task == null){
            printUpdateMessage(false);
            return;
        }

        Task beforeTask = task.copyTask();

        try{
            task.setDeadline(deadline);
        }catch (Exception e){
            System.out.println();
            System.out.println("Invalid date/time format. Use yyyy-MM-dd HH:mm");
            System.out.println();
            return;
        }

        if(!isUndoInProgress){
            undoStack.push(new UndoAction(ACTION_UPDATE, task.getTaskName(), beforeTask, task.copyTask()));
        }

        printUpdateMessage(true);
    }

    public void updateTaskPriority(String taskName, int priority){
        Task task = hashTable.get(taskName);
        if(task == null){
            printUpdateMessage(false);
            return;
        }

        if(priority != 1 && priority != 2){
            System.out.println();
            System.out.println("Invalid priority. Use 1 or 2");
            System.out.println();
            return;
        }

        Task beforeTask = task.copyTask();
        task.setPriority(priority);

        if(!isUndoInProgress){
            undoStack.push(new UndoAction(ACTION_UPDATE, task.getTaskName(), beforeTask, task.copyTask()));
        }

        printUpdateMessage(true);
    }

    public void updateTaskTime(String taskName, String taskTime){
        Task task = hashTable.get(taskName);
        if(task == null){
            printUpdateMessage(false);
            return;
        }

        Task beforeTask = task.copyTask();

        try{
            task.setTaskTime(taskTime);
        }catch (Exception e){
            System.out.println();
            System.out.println("Invalid date/time format. Use yyyy-MM-dd HH:mm");
            System.out.println();
            return;
        }

        if(!isUndoInProgress){
            undoStack.push(new UndoAction(ACTION_UPDATE, task.getTaskName(), beforeTask, task.copyTask()));
        }

        printUpdateMessage(true);
    }

    public void undoLastAction(){
        if(undoStack.isEmpty()){
            System.out.println();
            System.out.println("Nothing to undo");
            System.out.println();
            return;
        }

        UndoAction action = undoStack.pop();
        isUndoInProgress = true;

        try{
            if(ACTION_CREATE.equals(action.getActionType())){
                Task createdTask = action.getAfterTask();
                Task removed = removeTaskObject(createdTask.getTaskName());
                if(removed != null){
                    System.out.println();
                    System.out.println("Undo successful: create reverted");
                    System.out.println();
                }else{
                    System.out.println();
                    System.out.println("Undo failed: task not found");
                    System.out.println();
                }
            }else if(ACTION_DELETE.equals(action.getActionType())){
                Task deletedTask = action.getBeforeTask();
                boolean added = addTaskObject(deletedTask.copyTask());
                if(added){
                    System.out.println();
                    System.out.println("Undo successful: delete reverted");
                    System.out.println();
                }else{
                    System.out.println();
                    System.out.println("Undo failed: task with same name already exists");
                    System.out.println();
                }
            }else if(ACTION_UPDATE.equals(action.getActionType())){
                Task beforeTask = action.getBeforeTask();
                Task currentTask = hashTable.get(action.getTaskName());
                if(currentTask == null){
                    System.out.println();
                    System.out.println("Undo failed: task not found");
                    System.out.println();
                }else{
                    restoreTaskState(currentTask, beforeTask);
                    System.out.println();
                    System.out.println("Undo successful: update reverted");
                    System.out.println();
                }
            }
        }finally{
            isUndoInProgress = false;
        }
    }

    public void searchTask(String taskName){
        Task task = hashTable.get(taskName);
        if(task!=null){
            System.out.println();
            printTaskTable(new Task[]{task});
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

    public Task[] getTasksByDate(String currentDate){
        LocalDate inputDate;
        try{
            inputDate = LocalDate.parse(currentDate, DATE_FORMATTER);
        }catch (Exception e){
            return null;
        }

        Task[] allTasks = dll.getTaskArray();
        int matchCount = 0;

        for(int i = 0; i < allTasks.length; i++){
            if(allTasks[i].getTaskTime().toLocalDate().equals(inputDate)){
                matchCount++;
            }
        }

        Task[] matchingTasks = new Task[matchCount];
        int index = 0;

        for(int i = 0; i < allTasks.length; i++){
            if(allTasks[i].getTaskTime().toLocalDate().equals(inputDate)){
                matchingTasks[index++] = allTasks[i];
            }
        }

        return matchingTasks;
    }

    public void displayTasksByDate(String currentDate){
        Task[] matchingTasks = getTasksByDate(currentDate);

        if(matchingTasks == null){
            System.out.println();
            System.out.println("Invalid date format. Use yyyy-MM-dd");
            System.out.println();
            return;
        }

        if(matchingTasks.length == 0){
            System.out.println();
            System.out.println("NO TASKS FOUND FOR THIS DATE");
            System.out.println();
            return;
        }

        System.out.println();
        System.out.println("Tasks for date: " + currentDate);
        printTaskTable(matchingTasks);
        System.out.println();
    }

    private void printUpdateMessage(boolean updated){
        System.out.println();
        if(updated){
            System.out.println("Task successfully updated");
        }else{
            System.out.println("No such task");
        }
        System.out.println();
    }

    private void printTaskTable(Task[] tasks){
        System.out.printf(TASK_ROW_FORMAT, "ID", "Task Name", "Priority", "Deadline", "Task Time");
        System.out.println(TABLE_SEPARATOR);
        for(int i = 0; i < tasks.length; i++){
            System.out.printf(TASK_ROW_FORMAT,
                    tasks[i].getId(),
                    tasks[i].getTaskName(),
                    tasks[i].getPriority(),
                    tasks[i].getFormattedDeadline(),
                    tasks[i].getFormattedTaskTime());
        }
    }

    private boolean addTaskObject(Task task){
        if(hashTable.get(task.getTaskName()) != null){
            return false;
        }

        DoublyLinkedList.DLLNode dllNode = dll.addNode(task);
        System.out.println("successfully added task in dll");
        hashTable.put(task, dllNode);
        return true;
    }

    private Task removeTaskObject(String taskName){
        Task task = hashTable.get(taskName);
        if(task == null){
            return null;
        }

        DoublyLinkedList.DLLNode dllNode = hashTable.getDLLNode(taskName);
        if(dllNode == null){
            return null;
        }

        System.out.println("get dll node from hash table success");
        dll.deleteNode(dllNode);
        System.out.println("delete from dll success");
        hashTable.delete(taskName);
        System.out.println("delete from hash table success");
        return task;
    }

    private void restoreTaskState(Task currentTask, Task oldTask){
        currentTask.setTaskName(oldTask.getTaskName());
        currentTask.setDeadline(oldTask.getFormattedDeadline());
        currentTask.setTaskTime(oldTask.getFormattedTaskTime());
        currentTask.setPriority(oldTask.getPriority());
        currentTask.setCompleted(oldTask.isCompleted());
    }

}
