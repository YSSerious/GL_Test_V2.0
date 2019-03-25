package com.globallogic.test.tree.manager;

import com.globallogic.test.tree.observer.AbstractTreeManager;

import java.util.Objects;

public class StringTreeKey extends AbstractTreeManager {

    private String key;

    public StringTreeKey(String key) {
        this.key = key;
    }

    @Override
    public int getMaxChildCount() {
        if(getKey().length()<=2)
            return 2;
        else
            return getKey().length() < 6 ? 5 : 10;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringTreeKey that = (StringTreeKey) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {

        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "StringTreeKey{" +
                "key='" + key + '\'' +
                '}';
    }
}
