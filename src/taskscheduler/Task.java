package taskscheduler;

import taskscheduler.datastructures.linkedlist.DoublyLinkedList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private static int count;
    private final int id;
    private String taskName;

    private LocalDateTime deadline;
    private int priority;

    //task
    private boolean completed;

    public static void setCount(int maxId){
        count = maxId;
    }
    //task com
    private int status;

    public Task(String task_name, String deadline, int priority) {
        this.taskName = task_name.trim().toLowerCase();
        this.deadline = LocalDateTime.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.priority = priority;
        this.status = 0;
        this.completed = false;
        this.id = ++count;
    }

    public Task(int id, String taskName, LocalDateTime deadline, int priority, boolean completed) {
        this.id = id;
        this.taskName = taskName;
        this.deadline = deadline;
        this.priority = priority;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }


    //
//    public String getDeadline() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        return deadline.format(formatter);
//    }
    public LocalDateTime getDeadline() {
        return deadline;
    }
    //for printing
    public String getFormattedDeadline() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return deadline.format(formatter);
    }

    public int getStatus() {
        return status;
    }

    public void markCompleted() {
        status = 1;
    }

    public void markPending() {
        status = 0;
    }
    public int getPriority() {
        return priority;
    }

    public boolean isCompleted(){
        return this.completed;
    }

}