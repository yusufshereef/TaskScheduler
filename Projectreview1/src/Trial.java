public class Trial {

    // Simple Linked List Node for testing
    static class TaskNode {
        Task task;
        TaskNode next;

        TaskNode(Task task) {
            this.task = task;
            this.next = null;
        }
    }

    static TaskNode head = null;

    // Add task to linked list (at end)
    public static void addTask(Task task) {
        TaskNode newNode = new TaskNode(task);

        if (head == null) {
            head = newNode;
            return;
        }

        TaskNode temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }

        temp.next = newNode;
    }

    // Display all tasks
    public static void displayAllTasks() {
        TaskNode temp = head;

        if (temp == null) {
            System.out.println("No tasks available.");
            return;
        }

        while (temp != null) {
            System.out.println("ID: " + temp.task.getId() +
                    " | Name: " + temp.task.getTaskName() +
                    " | Deadline: " + temp.task.getDeadline() +
                    " | Priority: " + temp.task.getPriority() +
                    " | Status: " + (temp.task.getStatus() == 0 ? "Pending" : "Completed"));
            temp = temp.next;
        }
    }


    // Find task by ID
    public static Task findTaskById(int id) {
        TaskNode temp = head;

        while (temp != null) {
            if (temp.task.getId() == id) {
                return temp.task;
            }
            temp = temp.next;
        }

        return null;
    }

    public static void main(String[] args) {

        Stack_Completion stack = new Stack_Completion();

        // Create tasks
        Task t1 = new Task("Study DSA", "2026-05-10 18:00", 1);
        Task t2 = new Task("Complete Project", "2026-05-08 20:00", 2);
        Task t3 = new Task("Workout", "2026-05-09 07:00", 3);

        addTask(t1);
        addTask(t2);
        addTask(t3);

        System.out.println("All Tasks:");
        displayAllTasks();

        // Mark task 2 completed
        System.out.println("\nMarking Task 2 as Completed...");
        Task taskToComplete = findTaskById(2);

        if (taskToComplete != null) {
            stack.push(taskToComplete);
            taskToComplete.markCompleted();
        }

        displayAllTasks();
        // Show recently completed
        System.out.println("\nRecently Completed:");
        stack.displayRecent();

        // Undo
        System.out.println("\nUndo Last Completion:");
        stack.undo();

        displayAllTasks();
        DoublyTaskList list = new DoublyTaskList();
        list.addTask(new Task("Study", "2026-05-10 18:00", 2));
        list.addTask(new Task("Project", "2026-05-08 20:00", 1));
        SortByPriorityUsingQuickSort sorter = new SortByPriorityUsingQuickSort(list);
        System.out.println("By priority:");
        sorter.displaySorted();
        //by deadline
        SortByDeadlineUsingMergeSort sorterdead = new SortByDeadlineUsingMergeSort(list);
        System.out.println("By deadline:");
        sorterdead.displaySorted();

    }
}
