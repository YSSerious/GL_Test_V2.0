package com.globallogic.test.tree.observer;

import com.globallogic.test.tree.treecontainer.TreeContainer;

import java.util.Observable;
import java.util.Observer;

public class Subscriber implements Observer {

    private TreeContainer treeContainer;

    @Override
    public void update(Observable observable, Object arg) {
        treeContainer = (TreeContainer) observable;
        System.out.println("Message was came from Tree container: " + treeContainer.getSubscriberNotification());
    }
}
