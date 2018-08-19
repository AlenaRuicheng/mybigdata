package com.myself.mybigdata.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.myself.mybigdata.conf.RootConf;
import com.myself.mybigdata.model.User;
import com.myself.mybigdata.service.UserService;

public class TestService {
	@Test
	public void saveUser() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(RootConf.class);
		UserService us = (UserService) ac.getBean("userService");
		User user = new User();
		user.setName("John");
		user.setAge(12);
		us.saveEntity(user);
	}

	@Test
	public void findUsers() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(RootConf.class);
		UserService us = (UserService) ac.getBean("userService");
		//HQL��䣺from����������������Ǳ���
		List<User> list = us.findByHQL("from User");
		for(User u: list) {
			System.out.println(u.getName());
		}
	}
}
