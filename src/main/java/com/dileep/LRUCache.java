package com.dileep;

import java.util.HashMap;
import java.util.LinkedList;

record KeyValuePair(int key, int value) {
}

public class LRUCache {

    private LinkedList<KeyValuePair> lru;
    private HashMap<Integer, KeyValuePair> keyMap;
    private int capacity;
    private int size;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
    }

    public void put(int key, int val) {
        if (isFull()) {
            removeLru();
        }

        var newPair = new KeyValuePair(key, val);
        lru.addLast(newPair);
        keyMap.put(key, newPair);
        size++;
    }

    public int get(int key) {
        if (keyMap.containsKey(key)) {
            var pair = keyMap.get(key);
            lru.addLast(pair);
            return pair.value();
        }
        return -1;
    }

    private boolean isFull() {
        return capacity == size;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    private void removeLru() {
        if (isEmpty()) {
            System.out.println("Cache is already empty.");
            return;
        }

        int recent = lru.getFirst().key();
        lru.removeFirst();
        keyMap.remove(recent);
        size--;
    }

}
