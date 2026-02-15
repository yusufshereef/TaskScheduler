package taskscheduler;

import taskscheduler.datastructures.hashtable.HashTable;

public class TaskManager {
    private HashTable hashTable = new HashTable();
    public void createTask(String task_name, String deadline, int priority){
        if(hashTable.get(task_name)==null){
            Task task = new Task(task_name, deadline, priority);
            hashTable.put(task);
        }
    }
}
