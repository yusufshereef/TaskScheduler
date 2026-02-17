package taskscheduler.datastructures.sorting;

import taskscheduler.Task;

public class SortByPriorityUsingQuickSort {
    private Task[] taskArray;

    public SortByPriorityUsingQuickSort(Task[] taskArray) {


        this.taskArray = taskArray;
        quickSort(0, taskArray.length - 1);

    }

    private void quickSort(int low, int high) {

        if (low < high) {
            //task array [][][][][][][][][][][] created in this class and has all the
            int pivot = taskArray[high].getPriority();
            int i = low - 1;

            for (int j = low; j < high; j++) {

                if (taskArray[j].getPriority() <= pivot) {
                    i++;

                    Task temp = taskArray[i];
                    taskArray[i] = taskArray[j];
                    taskArray[j] = temp;
                }
            }

            Task temp = taskArray[i + 1];
            taskArray[i + 1] = taskArray[high];
            taskArray[high] = temp;

            int pivotIndex = i + 1;

            quickSort(low, pivotIndex - 1);
            quickSort(pivotIndex + 1, high);
        }
    }

    public void displaySorted() {
        String format = "%-5s | %-20s | %-10s | %-15s%n";
        System.out.printf(format, "ID", "Task Name", "Priority", "Deadline");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < taskArray.length; i++) {
            System.out.printf(format,
                    taskArray[i].getId(),
                    taskArray[i].getTaskName(),
                    taskArray[i].getPriority(),
                    taskArray[i].getFormattedDeadline());
        }
    }
}
