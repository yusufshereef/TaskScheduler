package taskscheduler;

public class Action {
    public enum Type {
        CREATE, DELETE, UPDATE_DEADLINE, UPDATE_PRIORITY
    }

    private final Type type;
    private final Task task;
    private final String taskName;
    private final String oldValue;

    public Action(Type type, Task task) {
        this.type = type;
        this.task = task;
        this.taskName = null;
        this.oldValue = null;
    }

    public Action(Type type, String taskName, String oldValue) {
        this.type = type;
        this.task = null;
        this.taskName = taskName;
        this.oldValue = oldValue;
    }

    public Type getType() {
        return type;
    }

    public Task getTask() {
        return task;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getOldValue() {
        return oldValue;
    }
}
