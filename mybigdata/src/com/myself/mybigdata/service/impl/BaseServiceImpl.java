package com.myself.mybigdata.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myself.mybigdata.dao.BaseDao;
import com.myself.mybigdata.service.BaseService;

@Transactional(isolation=Isolation.DEFAULT, propagation=Propagation.REQUIRED)
@EnableTransactionManagement
public abstract class BaseServiceImpl<T> implements BaseService<T> {
	private BaseDao<T> dao;
	private Class clazz;
	
	public BaseServiceImpl() {
		ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
		clazz = (Class) type.getActualTypeArguments()[0];
	}

	@Resource
	public void setDao(BaseDao<T> dao) {
		this.dao = dao;
	}
	
	public void saveEntity(T t) {
		dao.saveEntity(t);
	}

	public void updateEntity(T t) {
		dao.updateEntity(t);
	}

	public void deleteEntity(T t) {
		dao.deleteEntity(t);
	}

	public void executeByHQL(String hql, Object... objects) {
		dao.executeByHQL(hql, objects); 
	}

	public T getEntity(Integer id) {
		return dao.getEntity(id);
	}

	public List<T> findByHQL(String hql, Object... objects) {
		return dao.findByHQL(hql, objects);
	}
	
	public List<T> findAll(){
		return dao.findByHQL("from "+ clazz.getName());
	}

	public void deleteById(Integer id) {
		dao.executeByHQL("delete from "+clazz.getName() + " t where t.id =?", id);
	}
	
}
