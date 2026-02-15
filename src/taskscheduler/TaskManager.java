package taskscheduler;

public class TaskManager {
    public void createTask(String task_name, String deadline, int priority){
        Task task = new Task(task_name, deadline, priority);
    }
}
