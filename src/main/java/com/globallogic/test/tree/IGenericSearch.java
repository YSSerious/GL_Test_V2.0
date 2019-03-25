package com.globallogic.test.tree;

import java.util.List;
import java.util.Set;

public interface IGenericSearch<K, V> {
    List<V> getByKey(K key);
}
