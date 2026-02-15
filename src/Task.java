import java.util.Date;

public class Task {
    private static int count=0;
    private final int id;
    private String task;
    private Date deadline;
    private int priority;
    private int status;

    public Task(String task, Date deadline, int priority) {
        this.task = task;
        this.deadline = deadline;
        this.priority = priority;
        this.status = 0;
        this.id = ++count;
    }
}
