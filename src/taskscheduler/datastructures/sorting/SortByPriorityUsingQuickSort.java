package taskscheduler.datastructures.sorting;
import taskscheduler.Task;
import taskscheduler.datastructures.linkedlist.DoublyTaskList;

public class SortByPriorityUsingQuickSort {
    private Task[] taskArray;

    public SortByPriorityUsingQuickSort(Task[] taskArray) {


        this.taskArray = taskArray;

        // Copy tasks using simple loop


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

        for (int i = 0; i < taskArray.length; i++) {
            System.out.println("ID: " + taskArray[i].getId() + " | Name: " + taskArray[i].getTaskName() +" | Priority: " + taskArray[i].getPriority());
        }
    }

}
