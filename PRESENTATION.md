# TaskScheduler — Presentation Guide
> Use the sections below as individual slides in Gamma (or any presentation tool).  
> Each section contains **key bullet points** and **code snippets** for the corresponding file / folder.

---

## Slide 1 — Project Overview

### What is TaskScheduler?
- A **Java command-line Task Scheduling application**
- Users can create, delete, search, update, sort, and persist tasks
- Each task has a **name**, **deadline** (`yyyy-MM-dd HH:mm`), and **priority** (1 = HIGH, 2 = LOW)
- Data is persisted between sessions via a **CSV file**

### Repository Layout
```
TaskScheduler/
├── input.txt                  ← Sample batch-input file
├── data/
│   └── tasks.csv              ← Persistent storage (CSV)
└── src/taskscheduler/
    ├── Main.java              ← Entry point & CLI menu
    ├── Task.java              ← Task model
    ├── TaskManager.java       ← Central controller
    ├── filehandling/
    │   └── FileStorage.java   ← CSV read/write
    └── datastructures/
        ├── hashtable/
        │   └── HashTable.java
        ├── linkedlist/
        │   └── DoublyLinkedList.java
        └── sorting/
            ├── SortByDeadlineUsingMergeSort.java
            └── SortByPriorityUsingQuickSort.java
```

---

## Slide 2 — Architecture Diagram

```
┌──────────────────────────────────────────────┐
│           Main.java  (CLI / Entry point)      │
└──────────────────────┬───────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────┐
│          TaskManager.java  (Controller)       │
└────────┬───────────────────┬─────────────────┘
         │                   │
         ▼                   ▼
┌─────────────────┐  ┌──────────────────────┐  ┌─────────────────┐
│  HashTable      │  │  DoublyLinkedList    │  │  FileStorage    │
│  O(1) lookup    │←→│  insertion-order     │  │  CSV persist.   │
│  by task name   │  │  storage             │  │                 │
└─────────────────┘  └──────────────────────┘  └─────────────────┘
                                │
                          Task[] array
                         ┌──────┴──────┐
                         ▼             ▼
                    MergeSort     QuickSort
                   (deadline)    (priority)
```

### Design Highlights
- **Dual-structure** approach: HashTable for O(1) search + DoublyLinkedList for ordered storage
- **Cross-referencing**: HashTable nodes store pointers to DLL nodes → O(1) deletion
- Sorting is **non-destructive**: operates on a temporary array, leaving the DLL intact
- **Auto-incrementing IDs** survive restarts via CSV persistence

---

## Slide 3 — `Main.java` (Entry Point & CLI)

### Key Points
- Bootstraps the application: creates `TaskManager`, calls `initialize()` to reload saved tasks
- Presents a **9-option interactive menu** in a `do-while` loop
- Auto-saves on exit (option 9)

### Code Snippet
```java
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManager tm = new TaskManager();
        tm.initalize();          // load persisted tasks from CSV

        int ch = 0;
        do {
            System.out.println("1. Create Task(s)");
            System.out.println("2. Delete Task");
            System.out.println("3. Display all Tasks");
            System.out.println("4. Search Task");
            System.out.println("5. Update Task");
            System.out.println("6. Sort by Deadline");
            System.out.println("7. Sort by Priority");
            System.out.println("8. Save Tasks");
            System.out.println("9. Exit");
            ch = sc.nextInt();
            // ... delegate to TaskManager methods
        } while (ch != 9);

        tm.save();   // auto-save before exit
    }
}
```

---

## Slide 4 — `Task.java` (Model)

### Key Points
- Represents a single task with: `id`, `taskName`, `deadline`, `priority`, `completed`
- Task names are **normalized to lowercase** for case-insensitive operations
- `deadline` stored as `LocalDateTime`, parsed from `"yyyy-MM-dd HH:mm"` format
- Static `count` auto-increments IDs; `setCount()` restores the counter on file load

### Code Snippet
```java
public class Task {
    private static int count = 0;   // shared counter for auto-IDs

    private int id;
    private String taskName;
    private LocalDateTime deadline;
    private int priority;          // 1 = HIGH, 2 = LOW
    private boolean completed;

    public Task(String task_name, String deadline, int priority) {
        this.taskName  = task_name.trim().toLowerCase();
        this.deadline  = LocalDateTime.parse(
                             deadline,
                             DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.priority  = priority;
        this.completed = false;
        this.id        = ++count;
    }

    // Restore counter after loading from file
    public static void setCount(int maxId) { count = maxId; }

    public String getFormattedDeadline() { /* returns display string */ }
}
```

---

## Slide 5 — `TaskManager.java` (Controller)

### Key Points
- Owns all three data structures: `HashTable`, `DoublyLinkedList`, `FileStorage`
- **Create**: checks for duplicates via HashTable, then inserts into both DLL and HashTable
- **Delete**: looks up DLL node reference from HashTable → O(1) removal from both structures
- **Sort**: extracts `Task[]` array, sorts, then displays without modifying the DLL
- **Save / Initialize**: delegates to `FileStorage`

### Code Snippet
```java
public class TaskManager {
    private HashTable          hashTable   = new HashTable();
    private DoublyLinkedList   dll         = new DoublyLinkedList();
    private FileStorage        fileStorage = new FileStorage();

    public void createTask(String taskName, String deadline, int priority) {
        if (hashTable.get(taskName) == null) {              // duplicate check
            Task task = new Task(taskName, deadline, priority);
            DoublyLinkedList.DLLNode node = dll.addNode(task);
            hashTable.put(task, node);                      // store DLL reference
        }
    }

    public void deleteTask(String taskName) {
        DoublyLinkedList.DLLNode node = hashTable.getDLLNode(taskName);
        dll.deleteNode(node);           // O(1) — we have the exact node
        hashTable.delete(taskName);     // O(1) avg
    }

    public void sortByDeadline() {
        Task[] tasks = dll.getTaskArray();
        new SortByDeadlineUsingMergeSort(tasks).displaySorted();
    }

    public void sortByPriority() {
        Task[] tasks = dll.getTaskArray();
        new SortByPriorityUsingQuickSort(tasks).displaySorted();
    }
}
```

---

## Slide 6 — `filehandling/FileStorage.java` (Persistence)

### Key Points
- Reads from / writes to `data/tasks.csv`
- **On startup**: parses CSV, rebuilds both `DoublyLinkedList` and `HashTable`, restores ID counter
- **On save**: traverses DLL, serialises every task to CSV
- CSV format: `ID,Task,deadline,Priority,Completed`

### Code Snippet
```java
private static final String FILE_PATH = "data/tasks.csv";

public void writeToFile(DoublyLinkedList taskDLL) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
        writer.write("ID,Task,deadline,Priority,Completed");
        writer.newLine();
        for (Task task : taskDLL.getTaskArray()) {
            writer.write(task.getId() + "," + task.getTaskName() + ","
                       + task.getFormattedDeadline() + ","
                       + task.getPriority() + "," + task.isCompleted());
            writer.newLine();
        }
    }
}

public void loadDLLFromFile(DoublyLinkedList taskDLL, HashTable hashTable) {
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
        reader.readLine();          // skip header
        String line;
        int maxId = 0;
        while ((line = reader.readLine()) != null) {
            Task task = parseTask(line);
            DoublyLinkedList.DLLNode node = taskDLL.addNode(task);
            hashTable.put(task, node);
            if (task.getId() > maxId) maxId = task.getId();
        }
        Task.setCount(maxId);       // restore ID sequence
    }
}
```

### Sample CSV
```csv
ID,Task,deadline,Priority,Completed
7,complete test,2025-03-25 13:30,1,false
1,study maths,2025-02-17 08:28,1,false
```

---

## Slide 7 — `datastructures/hashtable/HashTable.java`

### Key Points
- **Separate chaining** collision resolution; fixed capacity = 7 buckets
- Each `HashNode` stores: `key` (task name), `Task` value, and a `DLLNode` reference
- Polynomial rolling hash: `hash = 7 * hash + charValue`
- Enables O(1) average-case: `get`, `put`, `delete`, `getDLLNode`

### Code Snippet
```java
private int getHash(String key) {
    int hash = 0;
    for (int i = 0; i < key.length(); i++)
        hash = (7 * hash) + key.charAt(i);
    int index = hash % capacity;
    return (index < 0) ? index + capacity : index;  // handle negative overflow
}

public void put(Task value, DoublyLinkedList.DLLNode dllNode) {
    String key   = value.getTaskName();
    int    index = getHash(key);
    HashNode node = new HashNode(key, value, dllNode);
    node.next      = hashTable[index];   // insert at chain head (O(1))
    hashTable[index] = node;
}

public Task get(String key) {
    key = key.trim().toLowerCase();
    int index = getHash(key);
    HashNode temp = hashTable[index];
    while (temp != null) {
        if (temp.key.equals(key)) return temp.value;
        temp = temp.next;
    }
    return null;                         // not found
}

public DoublyLinkedList.DLLNode getDLLNode(String key) { /* same traversal */ }
public void delete(String key)           { /* unlink from chain */ }
```

---

## Slide 8 — `datastructures/linkedlist/DoublyLinkedList.java`

### Key Points
- Doubly-linked list maintaining **insertion order** of tasks
- Each `DLLNode` holds `prev`, `task`, and `next` pointers
- `addNode()` inserts at **head** and returns the node reference (stored in HashTable)
- `deleteNode()` handles head / tail / middle cases in O(1)
- `getTaskArray()` converts the list to an array for sorting
- `display()` prints a formatted table to stdout

### Code Snippet
```java
public DLLNode addNode(Task task) {
    DLLNode node = new DLLNode(task);
    if (head != null) {
        head.prev = node;
        node.next = head;
    }
    head = node;
    size++;
    return node;        // caller stores this reference in HashTable
}

public void deleteNode(DLLNode node) {
    if (node == head) {
        head = head.next;
        if (head != null) head.prev = null;
    } else if (node.next == null) {     // tail
        node.prev.next = null;
    } else {                             // middle
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    size--;
}

public Task[] getTaskArray() {
    Task[] tasks = new Task[size];
    DLLNode cur = head;
    for (int i = 0; i < size; i++) { tasks[i] = cur.task; cur = cur.next; }
    return tasks;
}
```

---

## Slide 9 — `datastructures/sorting/SortByDeadlineUsingMergeSort.java`

### Key Points
- **Merge Sort** — O(n log n) guaranteed, stable sort
- Compares `LocalDateTime` objects using `.isBefore()`
- Sorts tasks chronologically (earliest deadline first)
- Sorting happens in the **constructor** on a temporary array (DLL is unchanged)

### Code Snippet
```java
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
    Task[] L = Arrays.copyOfRange(taskArray, left, mid + 1);
    Task[] R = Arrays.copyOfRange(taskArray, mid + 1, right + 1);
    int i = 0, j = 0, k = left;
    while (i < L.length && j < R.length) {
        if (L[i].getDeadline().isBefore(R[j].getDeadline()))
            taskArray[k++] = L[i++];    // earlier deadline first
        else
            taskArray[k++] = R[j++];
    }
    while (i < L.length) taskArray[k++] = L[i++];
    while (j < R.length) taskArray[k++] = R[j++];
}
```

---

## Slide 10 — `datastructures/sorting/SortByPriorityUsingQuickSort.java`

### Key Points
- **Quick Sort** — O(n log n) average, O(n²) worst case, in-place
- Pivot = last element's priority value
- Partitions by swapping elements relative to the pivot
- Priority 1 (HIGH) appears before Priority 2 (LOW) after sort
- Sorting happens in the **constructor** on a temporary array (DLL is unchanged)

### Code Snippet
```java
public SortByPriorityUsingQuickSort(Task[] taskArray) {
    this.taskArray = taskArray;
    quickSort(0, taskArray.length - 1);
}

private void quickSort(int low, int high) {
    if (low < high) {
        int pivotIndex = partition(low, high);
        quickSort(low, pivotIndex - 1);
        quickSort(pivotIndex + 1, high);
    }
}

private int partition(int low, int high) {
    int pivot = taskArray[high].getPriority();
    int i = low - 1;
    for (int j = low; j < high; j++) {
        if (taskArray[j].getPriority() <= pivot) {
            i++;
            Task temp      = taskArray[i];  // swap
            taskArray[i]   = taskArray[j];
            taskArray[j]   = temp;
        }
    }
    Task temp        = taskArray[i + 1];    // place pivot
    taskArray[i + 1] = taskArray[high];
    taskArray[high]  = temp;
    return i + 1;
}
```

---

## Slide 11 — `input.txt` & `data/tasks.csv` (Data Files)

### `input.txt` — Batch Input Template
- Plain-text file with **3 lines per task**: name, deadline, priority
- Used to feed multiple tasks without interactive prompting

```
study maths
2025-02-17 08:28
1
ride bicycle
2025-02-17 09:30
2
```

### `data/tasks.csv` — Persistent Storage
- CSV with 5 columns; written on every save; read on every startup
- ID sequence is preserved so IDs never repeat across sessions

```csv
ID,Task,deadline,Priority,Completed
7,complete test,2025-03-25 13:30,1,false
1,study maths,2025-02-17 08:28,1,false
```

---

## Slide 12 — Time & Space Complexity Summary

| Operation          | Time Complexity   | Space Complexity |
|--------------------|-------------------|-----------------|
| Create Task        | O(1) average      | O(1)            |
| Delete Task        | O(1) average      | O(1)            |
| Search Task        | O(1) average      | O(1)            |
| Display All Tasks  | O(n)              | O(1)            |
| Sort by Deadline   | O(n log n)        | O(n)            |
| Sort by Priority   | O(n log n) avg    | O(log n)        |
| Load from File     | O(n)              | O(n)            |
| Save to File       | O(n)              | O(n)            |

---

## Slide 13 — Key Design Decisions

1. **HashTable + DoublyLinkedList combo**  
   - HashTable gives fast O(1) lookup; DLL preserves insertion order and enables O(1) deletion when given a node reference.

2. **Cross-referencing nodes**  
   - HashTable entries hold a direct pointer to the corresponding DLL node, making deletions O(1) without scanning the list.

3. **Non-destructive sorting**  
   - `getTaskArray()` produces a snapshot; sorting never mutates the live DLL.

4. **CSV persistence with ID continuity**  
   - `Task.setCount(maxId)` re-seeds the ID counter on load so IDs are never reused.

5. **Lowercase normalization**  
   - All task names are lowercased on creation, making search and updates case-insensitive.
