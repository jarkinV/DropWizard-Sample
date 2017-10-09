
package com.softserve.dao;

import java.util.List;

public interface BaseDao<K> {
    K findOne(int id);

    List<K> findAll();

    void save(K k);

    boolean remove(K k);

    boolean removeById(int id);
}
