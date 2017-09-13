package com.softserve.dao;

import java.util.List;

public interface BaseDao<K> {
	K findOne(int id);
	List<K> findAll();
	boolean save(K k);
	boolean remove(K k);
	boolean removeById(int id);
}
