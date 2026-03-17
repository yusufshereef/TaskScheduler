package taskscheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private static int count;
    private final int id;
    private String taskName;
    private LocalDateTime deadline;
    private int priority;
    private boolean completed;

    public static void setCount(int maxId){
        count = maxId;
    }

    public Task(String task_name, String deadline, int priority) {
        this.taskName = task_name.trim().toLowerCase();
        this.deadline = LocalDateTime.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.priority = priority;
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

    public String getTaskName() {
        return taskName;
    }
    //
    public String getFormattedDeadline() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return deadline.format(formatter);
    }

    public int getPriority(){
        return this.priority;
    }

    public void setDeadline(String deadline){
        this.deadline = LocalDateTime.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public LocalDateTime getDeadline(){
        return this.deadline;
    }

    public void setPriority(int priority){
        if(priority==1 || priority==2)
            this.priority = priority;
        else
            System.out.println("Invalid priority");
    }

    public int getId(){
        return this.id;
    }

    public boolean isCompleted(){
        return this.completed;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }
}