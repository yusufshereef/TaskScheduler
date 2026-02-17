package taskscheduler;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManager tm = new TaskManager();
        int ch = 0;
        do{
            System.out.println("=================================");
            System.out.println("         TASK SCHEDULER          ");
            System.out.println("=================================");
            System.out.println("1. Create Task");
            System.out.println("2. Delete Task");
            System.out.println("3. display ");
            System.out.println("4. search task");
            System.out.println("5. update task details ");
            System.out.println("8. exit: ");
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
                    switch(choice){
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
            }
        }while(ch!=8);
    }
}
