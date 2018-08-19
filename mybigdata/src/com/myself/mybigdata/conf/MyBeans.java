package com.myself.mybigdata.conf;

 import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import kafka.Kafka;

@Component
public class MyBeans {
	@Bean("properties")
	public Properties getProperties() {
		try {
			Properties properties = new Properties();
			ClassPathResource classPathResource = new ClassPathResource("properties");
			properties.load(classPathResource.getInputStream());
			return properties;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Bean
	public DataSource getDataSource(Properties properties) {
		ComboPooledDataSource comboPooledDataSource = null;
		try {
			comboPooledDataSource = new ComboPooledDataSource();
			comboPooledDataSource.setDriverClass(properties.getProperty("jdbc.driverclass"));
			comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbc.url"));
			comboPooledDataSource.setUser(properties.getProperty("jdbc.username"));
			comboPooledDataSource.setPassword(properties.getProperty("jdbc.password"));
			
			comboPooledDataSource.setMaxPoolSize(Integer.parseInt(properties.getProperty("c3p0.pool.size.max")));
			comboPooledDataSource.setMinPoolSize(Integer.parseInt(properties.getProperty("c3p0.pool.size.min")));
			comboPooledDataSource.setInitialPoolSize(Integer.parseInt(properties.getProperty("c3p0.pool.size.ini")));
			comboPooledDataSource.setAcquireIncrement(Integer.parseInt(properties.getProperty("c3p0.pool.size.increment")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comboPooledDataSource;
	}
	
	@Bean("sessionFactory")
	public LocalSessionFactoryBean getSessionFactory(DataSource dataSource, Properties properties) {
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		localSessionFactoryBean.setDataSource(dataSource);
		localSessionFactoryBean.setHibernateProperties(properties);
		localSessionFactoryBean.setMappingDirectoryLocations(new ClassPathResource("com/myself/mybigdata/model"));
		return localSessionFactoryBean;
	}
	
	@Bean("transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
		hibernateTransactionManager.setSessionFactory(sessionFactory);
		return hibernateTransactionManager;
	}
}
