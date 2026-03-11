package taskscheduler.filehandling;

import taskscheduler.Task;
import taskscheduler.datastructures.hashtable.HashTable;
import taskscheduler.datastructures.linkedlist.DoublyLinkedList;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileStorage {
    private static final String FILE_PATH = "data/tasks.csv";
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void writeToFile(DoublyLinkedList taskDLL){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))){

            writer.write("ID,Task,deadline,Priority,Completed");
            writer.newLine();

            Task[] tasks = taskDLL.getTaskArray();

            for(Task task : tasks){
                writer.write(taskEntry(task));
                writer.newLine();
                System.out.println("saving tasks.....");
            }

            System.out.println(taskDLL.getSize()+" tasks saved to file");

        }catch (IOException e){
            System.out.println("Failed to write CSV file: " + e.getMessage());
        }
    }

    private String taskEntry(Task task){
        String entry = task.getId()+","+task.getTaskName()+","+task.getFormattedDeadline()+","+task.getPriority()+","+task.isCompleted();
        return entry;
    }

    public void loadDLLFromFile(DoublyLinkedList taskDLL, HashTable hashTable){

        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))){

            String line;
            boolean firstLine = true;

            int maxId = 0;

            while((line = reader.readLine())!=null){
                if(firstLine){
                    firstLine = false;
                    continue;
                }
                Task task = parseTask(line);
                if(task.getId()>maxId) maxId = task.getId();
                DoublyLinkedList.DLLNode dllNode = taskDLL.addNode(task);
                hashTable.put(task, dllNode);

                System.out.println("tasks loading.....");
            }

            Task.setCount(maxId);

            System.out.println(taskDLL.getSize() + " tasks loaded from file");

        }catch (IOException e) {
            System.out.println("Failed to load file: "+e.getMessage());
        }
    }

    private Task parseTask(String line) {
        String[] fields = line.split(",");
        int id = Integer.parseInt(fields[0]);
        String taskName = fields[1];
        LocalDateTime deadline = LocalDateTime.parse(fields[2], formatter);
        int priority = Integer.parseInt(fields[3]);
        boolean completed = Boolean.parseBoolean(fields[4]);
        Task task = new Task(id, taskName, deadline, priority, completed);
        return task;
    }
}
