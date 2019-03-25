package com.globallogic.test.tree;

public interface ITreeContainer<K, V> extends IGenericSearch<K, V> {
    void put(K parentKey, K key, V val);
    void remove(K key);
    void removeChildren(K key);
    ITreeContainer<K, V> getSubTree(K key);
    String printTreeStructure();
    boolean isEmpty();
}
