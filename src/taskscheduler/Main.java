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
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            ch = sc.nextInt();
            switch (ch){
                case 1:
                    System.out.print("Enter Task: ");
                    String task = sc.nextLine();
                    System.out.print("\nenter deadline (yyyy-MM-dd HH:mm): ");
                    String deadline = sc.nextLine();
                    System.out.print("\nenter priority (1-HIGH, 2-LOW): ");
                    int priority = sc.nextInt();
                    tm.createTask(task, deadline, priority);
            }
        }while(ch!=3);
    }
}
