package com.myself.mybigdata.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.myself.mybigdata.dao.BaseDao;
import com.myself.mybigdata.hbase.PvProcessor;
import com.myself.mybigdata.model.User;
import com.myself.mybigdata.service.UserService;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Resource(name="userDao")
	public void setDao(BaseDao<User> dao) {
		super.setDao(dao);
	}

	public User validateLoginInfo(String username, String password) {
		String hql = "from User u where u.name=? and u.password=?";
		List<User> list = this.findByHQL(hql, username, password);
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<String[]> queryTableBatch(String tableName) {
		PvProcessor processor = new PvProcessor();
		return processor.queryTableBatch(tableName);
	}

}
