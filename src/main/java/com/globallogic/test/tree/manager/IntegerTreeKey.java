package com.globallogic.test.tree.manager;

import com.globallogic.test.tree.observer.AbstractTreeManager;

import java.util.Objects;

public class IntegerTreeKey extends AbstractTreeManager{
    private Integer key;

    public IntegerTreeKey(Integer key) {
        this.key = key;
    }

    @Override
    public int getMaxChildCount() {
        if(getKey()<=2)
            return 2;
        else
            return getKey() < 6 ? 5 : 10;
    }

    public Integer getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerTreeKey that = (IntegerTreeKey) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {

        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "IntegerTreeKey{" +
                "key=" + key +
                '}';
    }
}
