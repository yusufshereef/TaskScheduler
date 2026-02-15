package taskscheduler.datastructures.hashtable;

import taskscheduler.Task;

public class HashTable {
    class HashNode{
        String key;
        Task value;
        HashNode next;

        public HashNode(String key, Task value){
            this.key = key;
            this.value = value;
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

    public void put(String key, Task value){
        int index = getHash(key);
        HashNode head = hashTable[index];
        HashNode temp = head;

        while(temp!=null){
            if(temp.key.equals(key)){
                System.out.println("Task already exits");
                return;
            }
            temp = temp.next;
        }

        HashNode node = new HashNode(key, value);
        node.next = head;
        hashTable[index] = node;
    }

    public Task get(String key){
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


}
