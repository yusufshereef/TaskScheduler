package taskscheduler;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManager tm = new TaskManager();
        int ch = -1;
        tm.initalize();
        do{
            System.out.println("=================================");
            System.out.println("         TASK SCHEDULER          ");
            System.out.println("=================================");
            System.out.println("1.  Create Task");
            System.out.println("2.  Delete Task");
            System.out.println("3.  Display Tasks");
            System.out.println("4.  Search Task");
            System.out.println("5.  Update Task Details");
            System.out.println("6.  Sort Tasks by Deadline");
            System.out.println("7.  Sort Tasks by Priority");
            System.out.println("8.  Save Tasks to File");
            System.out.println("--- Queue Operations ---");
            System.out.println("9.  Schedule Task for Execution (Enqueue)");
            System.out.println("10. Execute Next Scheduled Task (Dequeue)");
            System.out.println("11. View Execution Queue");
            System.out.println("--- Stack Operations ---");
            System.out.println("12. Mark Task as Completed");
            System.out.println("13. Undo Last Task Completion");
            System.out.println("14. View Completed Tasks History");
            System.out.println("0.  Exit");
            System.out.print("Enter your choice: ");
            ch = sc.nextInt();
            sc.nextLine();
            String taskName;
            String deadline;
            int priority;
            switch (ch){
                case 1:
                    System.out.print("enter number of tasks you want to create: ");
                    int count = sc.nextInt();
                    sc.nextLine();
                    while(count-- > 0){
                        System.out.print("\nEnter Task: ");
                        taskName = sc.nextLine();
                        System.out.print("\nEnter deadline (yyyy-MM-dd HH:mm): ");
                        deadline = sc.nextLine();
                        System.out.print("\nEnter priority (1-HIGH, 2-LOW): ");
                        priority = sc.nextInt();
                        sc.nextLine();
                        tm.createTask(taskName, deadline, priority);
                    }
                    break;
                case 2:
                    System.out.print("enter taskName to be deleted: ");
                    taskName = sc.nextLine();
                    tm.deleteTask(taskName);
                    break;
                case 3:
                    tm.displayTasks();
                    break;
                case 4:
                    System.out.print("search task by Name: ");
                    taskName = sc.nextLine();
                    tm.searchTask(taskName);
                    break;
                case 5:
                    System.out.println("1. update task deadline");
                    System.out.println("2. update task priority");
                    System.out.print("enter choice: ");
                    int choice = sc.nextInt();
                    sc.nextLine();
                    switch(choice) {
                        case 1:
                            System.out.print("\nEnter task name(to be updated): ");
                            taskName = sc.nextLine();
                            System.out.print("\nEnter new deadline (yyyy-MM-dd HH:mm):");
                            deadline = sc.nextLine();
                            tm.updateTaskDeadline(taskName, deadline);
                            break;
                        case 2:
                            System.out.print("\nEnter task name(to be updated): ");
                            taskName = sc.nextLine();
                            System.out.print("\nEnter new priority(1-HIGH, 2-LOW) : ");
                            priority = sc.nextInt();
                            tm.updateTaskPriority(taskName, priority);
                            break;
                    }
                    break;
                case 6:
                    System.out.println();
                    System.out.println("Tasks sorted by deadline");
                    tm.sortByDeadline();
                    break;
                case 7:
                    System.out.println();
                    System.out.println("Tasks sorted by priority");
                    tm.sortByPriority();
                    break;
                case 8:
                    tm.save();
                    break;
                case 9:
                    System.out.print("Enter task name to schedule for execution: ");
                    taskName = sc.nextLine();
                    tm.scheduleTask(taskName);
                    break;
                case 10:
                    tm.executeNextTask();
                    break;
                case 11:
                    tm.displayQueue();
                    break;
                case 12:
                    System.out.print("Enter task name to mark as completed: ");
                    taskName = sc.nextLine();
                    tm.markTaskCompleted(taskName);
                    break;
                case 13:
                    tm.undoLastCompletion();
                    break;
                case 14:
                    tm.displayCompletedTasks();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }while(ch!=0);
        tm.save();
    }
}
