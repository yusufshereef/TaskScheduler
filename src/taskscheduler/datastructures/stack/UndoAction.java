package taskscheduler.datastructures.stack;

import taskscheduler.Task;

public class UndoAction {
    private String actionType;
    private String taskName;
    private Task beforeTask;
    private Task afterTask;

    public UndoAction(String actionType, String taskName, Task beforeTask, Task afterTask) {
        this.actionType = actionType;
        this.taskName = taskName;
        this.beforeTask = beforeTask;
        this.afterTask = afterTask;
    }

    public String getActionType() {
        return actionType;
    }

    public String getTaskName() {
        return taskName;
    }

    public Task getBeforeTask() {
        return beforeTask;
    }

    public Task getAfterTask() {
        return afterTask;
    }
}

