package taskscheduler.datastructures.sorting;

import taskscheduler.Task;

public class SortByDeadlineUsingMergeSort {

    private Task[] taskArray;

    public SortByDeadlineUsingMergeSort(Task[] taskArray) {
        this.taskArray = taskArray;

        mergeSort(0, taskArray.length - 1);

    }

    private void mergeSort(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(left, mid);
            mergeSort(mid + 1, right);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {

        int n1 = mid - left + 1;
        int n2 = right - mid;

        Task[] leftArray = new Task[n1];
        Task[] rightArray = new Task[n2];

        for (int i = 0; i < n1; i++)
            leftArray[i] = taskArray[left + i];

        for (int j = 0; j < n2; j++)
            rightArray[j] = taskArray[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (leftArray[i].getDeadline()
                    .isBefore(rightArray[j].getDeadline())) {

                taskArray[k++] = leftArray[i++];
            } else {
                taskArray[k++] = rightArray[j++];
            }
        }

        while (i < n1) taskArray[k++] = leftArray[i++];
        while (j < n2) taskArray[k++] = rightArray[j++];
    }

    public void displaySorted() {
        String format = "%-5s | %-20s | %-10s | %-15s%n";
        System.out.printf(format, "ID", "Task Name", "Priority", "Deadline");
        System.out.println("------------------------------------------------------------");
        for (Task task : taskArray) {
            System.out.printf(format,
                    task.getId(),
                    task.getTaskName(),
                    task.getPriority(),
                    task.getFormattedDeadline());
        }
    }
}
