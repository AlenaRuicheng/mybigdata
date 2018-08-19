package com.myself.mybigdata.service;

import java.util.List;
import java.util.Map;

import com.myself.mybigdata.model.User;

public interface UserService extends BaseService<User> {
	public User validateLoginInfo(String username, String password);
	public List<String[]> queryTableBatch(String tableName);
}
