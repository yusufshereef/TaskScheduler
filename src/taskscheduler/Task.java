package taskscheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private static int count;
    private final int id;
    private String taskName;
    private LocalDateTime deadline;
    private int priority;
    private int status;

    public Task(String task_name, String deadline, int priority) {
        this.taskName = task_name.trim().toLowerCase();
        this.deadline = LocalDateTime.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.priority = priority;
        this.status = 0;
        this.id = ++count;
    }

    public String getTaskName() {
        return taskName;
    }
}