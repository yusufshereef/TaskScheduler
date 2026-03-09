package taskscheduler;
import taskscheduler.datastructures.sorting.SortByDeadlineUsingMergeSort;
import taskscheduler.datastructures.linkedlist.DoublyLinkedList;
import taskscheduler.datastructures.stacks.Stack_Completion;
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
            System.out.println("3. Display Tasks");
            System.out.println("4. Delete Task By ID");
            System.out.println("6. Sort by Deadline");
            System.out.println("7. Sort by Priority");
            System.out.println("9. Undo Delete");
            System.out.println("10. Redo Delete");
            System.out.println("11. Recently Deleted Tasks");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            ch = sc.nextInt();
            sc.nextLine();
            String taskName;
            switch (ch){
                case 1:
                    System.out.print("enter number of tasks you want to create: ");
                    int count = sc.nextInt();
                    sc.nextLine();
                    while(count-- > 0){
                       // System.out.print("\nEnter Task: ");
                        taskName = sc.nextLine();
                       // System.out.print("\nEnter deadline (yyyy-MM-dd HH:mm): ");
                        String deadline = sc.nextLine();
                       // System.out.print("\nEnter priority (1-HIGH, 2-LOW): ");
                        int priority = sc.nextInt();
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
                    System.out.print("Enter Task ID to delete: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    tm.deleteTaskById(id);
                    break;
                case 6:
                    System.out.println("\n--- Sorted by Deadline ---");
                   tm.mergesort();
                    break;
                case 7:
                    System.out.println("\n--- Sort by Priority ---");
                    tm.quicksort();
                    break;
                case 9:
                    tm.undoDelete();
                    break;

                case 10:
                    tm.redoDelete();
                    break;

                case 11:
                    tm.showRecentlyDeleted();
                    break;



            }
        }while(ch!=8);
    }
}
