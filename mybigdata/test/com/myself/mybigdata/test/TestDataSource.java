package com.myself.mybigdata.test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.myself.mybigdata.conf.RootConf;

public class TestDataSource {
	@Test
	public void getConn() throws SQLException {
		ApplicationContext ac = new AnnotationConfigApplicationContext(RootConf.class);
		DataSource ds = (DataSource) ac.getBean("dataSource");
		Connection conn = ds.getConnection();
		System.out.println(conn);
	}
}
