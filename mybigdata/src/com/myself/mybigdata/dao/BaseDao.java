package com.myself.mybigdata.dao;

import java.util.List;

public interface BaseDao<T> {
	public void saveEntity(T t);
	public void updateEntity(T t);
	public void deleteEntity(T t);
	public void executeByHQL(String hql, Object...objects);
	
	public T getEntity(Integer id);
	public List<T> findByHQL(String hql, Object...objects);
}
