package com.myself.mybigdata.service;

import java.util.List;

public interface BaseService<T> {
	public void saveEntity(T t);
	public void updateEntity(T t);
	public void deleteEntity(T t);
	public void deleteById(Integer id);
	public void executeByHQL(String hql, Object...objects);
	
	public T getEntity(Integer id);
	public List<T> findByHQL(String hql, Object...objects);
	public List<T> findAll();
}
