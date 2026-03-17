package taskscheduler;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManager tm = new TaskManager();
        int ch = 0;
        tm.initalize();
        do{
            System.out.println("=================================");
            System.out.println("         TASK SCHEDULER          ");
            System.out.println("=================================");
            System.out.println("1. Create Task");
            System.out.println("2. Delete Task");
            System.out.println("3. display ");
            System.out.println("4. search task");
            System.out.println("5. update task details ");
            System.out.println("6. sort tasks by deadline");
            System.out.println("7. sort tasks by priority");
            System.out.println("8. show tasks by date");
            System.out.println("9. save tasks to file");
            System.out.println("10. undo last action");
            System.out.println("11. exit");
            System.out.print("Enter your choice: ");
            ch = sc.nextInt();
            sc.nextLine();
            String taskName;
            String deadline;
            String taskTime;
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
                        System.out.print("\nEnter exact task time (yyyy-MM-dd HH:mm): ");
                        taskTime = sc.nextLine();
                        System.out.print("\nEnter priority (1-HIGH, 2-LOW): ");
                        priority = sc.nextInt();
                        sc.nextLine();
                        tm.createTask(taskName, deadline, taskTime, priority);
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
                    System.out.println("2. update task time");
                    System.out.println("3. update task priority");
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
                            System.out.print("\nEnter new task time (yyyy-MM-dd HH:mm):");
                            taskTime = sc.nextLine();
                            tm.updateTaskTime(taskName, taskTime);
                            break;
                        case 3:
                            System.out.print("\nEnter task name(to be updated): ");
                            taskName = sc.nextLine();
                            System.out.print("\nEnter new priority(1-HIGH, 2-LOW) : ");
                            priority = sc.nextInt();
                            sc.nextLine();
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
                    System.out.print("Enter date (yyyy-MM-dd): ");
                    String currentDate = sc.nextLine();
                    tm.displayTasksByDate(currentDate);
                    break;
                case 9:
                    tm.save();
                    break;
                case 10:
                    tm.undoLastAction();
                    break;
            }
        }while(ch!=11);
        tm.save();
    }
}
