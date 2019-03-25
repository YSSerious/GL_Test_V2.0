package com.globallogic.test.tree.treecontainer;

import com.globallogic.test.tree.ITreeContainer;
import com.globallogic.test.tree.exception.EmptyKeyException;
import com.globallogic.test.tree.exception.MaxChildrenAmountException;
import com.globallogic.test.tree.observer.AbstractTreeManager;
import com.globallogic.test.tree.observer.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

public class TreeContainer<K extends AbstractTreeManager, V> extends Observable implements ITreeContainer<K, V> {

    private Node<K, V> root;
    private String subscriberNotification;

    public TreeContainer() {
        addObserver(new Subscriber());
    }

    public TreeContainer(Node<K, V> root) {
        this.root = root;
        addObserver(new Subscriber());
    }

    @Override
    public void put(K parentKey, K key, V val) {
        if (key == null)
            throw new EmptyKeyException("Key is null!");
        if (root == null) {
            root = new Node<>(key, val);
            setSubscriberNotification("Root node was created: [" + key + " = " + val +"]");
        } else {
            if (parentKey != null)
                put(root, parentKey, key, val);
        }
    }

    public void put(Node<K, V> node, K parentKey, K key, V val) {
        if (node != null) {
            if (node.key.equals(parentKey)){
                node.children.add(new Node<>(key, val));
                setSubscriberNotification("New node was created: [" + key + " = " + val +"]. Parent node key is: " + "[" + parentKey + "]");
            }
            for (int i = 0; i < node.children.size(); i++) {
                put(node.children.get(i), parentKey, key, val);
            }
        }

    }

    @Override
    public void remove(K key) {
        if (key == null)
            throw new EmptyKeyException("Key is null!");
        if (root.key.equals(key)) {
            root = null;
        } else {
            root.children = remove(root.children, key);
        }

    }

    @Override
    public void removeChildren(K key) {
        if (key == null)
            throw new EmptyKeyException("Key is null!");
        removeChildren(root, key);
    }

    private void removeChildren(Node<K, V> node, K key) {
        if (node.key.equals(key)){
            node.deleteChildren();
            setSubscriberNotification("Children of node with key: [" + key +"] was removed");
        }
        if (!node.children.isEmpty()){
            for(Node<K, V> child: node.children)
                removeChildren(child, key);
        }
    }

    private List<Node<K, V>> remove(List<Node<K, V>> children, K key) {
        if (children.isEmpty())
            return children;
        children = filterChildren(children, key);
        for (Node<K, V> aChildren : children) {
            aChildren.children = remove(aChildren.children, key);
        }
        return children;
    }

    private List<Node<K, V>> filterChildren(List<Node<K, V>> children, K key) {
        List<Node<K, V>> filteredChildren = children.stream().filter(n -> !n.key.equals(key)).collect(Collectors.toList());
        if(filteredChildren.size()!=children.size()){
            setSubscriberNotification("Element with key ["+ key +"] was removed.");
        }
        return filteredChildren;
    }

    @Override
    public TreeContainer<K, V> getSubTree(K key) {
        Node<K, V> searchedNode = searchNode(key);
        if(searchedNode == null){
            setSubscriberNotification("Sub tree with parent key : [" + key +" ] wasn't found");
            return null;
        }else{
            setSubscriberNotification("Sub tree with parent key : [" + key +" ] was found");
            return new TreeContainer<>(searchedNode.copy());
        }
    }

    //Finds the first suitable node, coz this structure may contains duplicates.
    private Node<K, V> searchNode(K key){
       return searchNode(root, key);
    }

    private Node<K, V> searchNode(Node<K, V> node, K key){
        if(node.key.equals(key))
            return node;
        for(Node<K, V> child: node.children){
            Node<K, V> n = searchNode(child, key);
            if(n!=null)
                return n;
        }
        return null;
    }

    @Override
    public String printTreeStructure(){
        return printTreeStructure(root);

    }

    @Override
    public boolean isEmpty() {
        return root==null;
    }

    public String printTreeStructure(Node<K, V> node){
        if (node == null)
            return "";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n[").append(node.key).append(" = ").append(node.value);
        for(Node<K, V> k: node.children){
            stringBuilder.append(printTreeStructure(k));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void setSubscriberNotification(String subscriberNotification) {
        this.subscriberNotification = subscriberNotification;
        setChanged();
        notifyObservers();
    }

    public String getSubscriberNotification() {
        return subscriberNotification;
    }

    @Override
    public List<V> getByKey(K key) {
        if (key == null)
            throw new EmptyKeyException("Key is null!");
        return root == null ? null : getByKey(root, key);
    }

    private List<V> getByKey(Node<K, V> node, K key) {
        List<V> values = new ArrayList<>();
        if(node.key.equals(key)){
            values.add(node.value);
            setSubscriberNotification("Element with key : [" + key +" ] was found");
        }
        for(Node<K, V> n: node.children){
            values.addAll(getByKey(n ,key));
        }
        return values;
    }

    public static class Node<K extends AbstractTreeManager, V> {
        K key;
        V value;
        List<Node<K, V>> children;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            children = new CustomArrayList<>();
        }

        Node(K key, V value, List<Node<K, V>> children) {
            this.key = key;
            this.value = value;
            this.children = children;
        }

        Node<K, V> copy() {
            List<Node<K, V>> copChildren = new CustomArrayList<>();
            for(Node<K, V> child: children){
                copChildren.add(child.copy());
            }
            return new Node<>(key, value, copChildren);
        }

        void deleteChildren() {
            this.children = new CustomArrayList<>();
        }

        public String toString() {
            return key + "=" + value;
        }

        public class CustomArrayList<T> extends ArrayList<T>{
            public final boolean add(T e) {
                if (this.size() == key.getMaxChildCount()){
                    throw new MaxChildrenAmountException("Chosen Node " + key + " is already contains maximum children amount ("+key.getMaxChildCount()+")");
                }
                return super.add(e);
            }

        }
    }
}
