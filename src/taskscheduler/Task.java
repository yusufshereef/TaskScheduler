package taskscheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private static int count;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final int id;
    private String taskName;
    private LocalDateTime deadline;
    private LocalDateTime taskTime;
    private int priority;
    private boolean completed;

    public static void setCount(int maxId){
        count = maxId;
    }

    public Task(String task_name, String deadline, String taskTime, int priority) {
        this.taskName = task_name.trim().toLowerCase();
        this.deadline = LocalDateTime.parse(deadline, FORMATTER);
        this.taskTime = LocalDateTime.parse(taskTime, FORMATTER);
        this.priority = priority;
        this.completed = false;
        this.id = ++count;
    }

    public Task(int id, String taskName, LocalDateTime deadline, LocalDateTime taskTime, int priority, boolean completed) {
        this.id = id;
        this.taskName = taskName;
        this.deadline = deadline;
        this.taskTime = taskTime;
        this.priority = priority;
        this.completed = completed;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName){
        this.taskName = taskName.trim().toLowerCase();
    }

    public String getFormattedDeadline() {
        return deadline.format(FORMATTER);
    }

    public String getFormattedTaskTime() {
        return taskTime.format(FORMATTER);
    }

    public int getPriority(){
        return this.priority;
    }

    public void setDeadline(String deadline){
        this.deadline = LocalDateTime.parse(deadline, FORMATTER);
    }

    public LocalDateTime getDeadline(){
        return this.deadline;
    }

    public void setTaskTime(String taskTime){
        this.taskTime = LocalDateTime.parse(taskTime, FORMATTER);
    }

    public LocalDateTime getTaskTime(){
        return this.taskTime;
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

    public Task copyTask(){
        return new Task(id, taskName, deadline, taskTime, priority, completed);
    }
}