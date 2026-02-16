package taskscheduler.datastructures.hashtable;

import taskscheduler.Task;
import taskscheduler.datastructures.linkedlist.DoublyLinkedList;

public class HashTable {
    public class HashNode{
        String key;
        Task value;
        DoublyLinkedList.DLLNode dllNode;
        HashNode next;

        public HashNode(String key, Task value, DoublyLinkedList.DLLNode dllNode){
            this.key = key;
            this.value = value;
            this.dllNode = dllNode;
        }
    }

    private HashNode[] hashTable;
    private final int capacity = 29;

    public HashTable(){
        hashTable = new HashNode[capacity];
    }

    private int getHash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (31 * hash) + key.charAt(i);
        }
        // Even if 'hash' is negative due to overflow,
        int index = hash % 29;
        if (index < 0) {
            index += 29;
        }
        return index;
    }

    public void put(Task value, DoublyLinkedList.DLLNode dllnode){
        String key = value.getTaskName();
        int index = getHash(key);
        HashNode head = hashTable[index];
        HashNode node = new HashNode(key, value, dllnode);
        node.next = head;
        hashTable[index] = node;
    }

    public Task get(String key){
        key = key.trim().toLowerCase();
        int index = getHash(key);
        HashNode head = hashTable[index];
        HashNode temp = head;
        while(temp != null){
            if(temp.key.equals(key)){
                return temp.value;
            }
            temp = temp.next;
        }
        return null;
    }

    public DoublyLinkedList.DLLNode getDLLNode(String key){
        int index = getHash(key);
        HashNode head = hashTable[index];
        HashNode temp = head;
        while(!temp.key.equals(key)){
            temp = temp.next;
        }
        DoublyLinkedList.DLLNode dllNode = temp.dllNode;
        return dllNode;
    }

    public Task delete(String key){
        int index = getHash(key);
        HashNode head = hashTable[index];
        if(head == null) return null;
        if(head.key.equals(key)){
            hashTable[index] = head.next;
            return head.value;
        }
        HashNode temp = head;
        while(!temp.next.key.equals(key)){
            temp = temp.next;
        }
        Task task = temp.next.value;
        temp.next = temp.next.next;
        return task;
    }


}
