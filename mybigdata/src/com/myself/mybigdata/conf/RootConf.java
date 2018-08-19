package com.myself.mybigdata.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = {
		"com.myself.mybigdata.conf", 
		"com.myself.mybigdata.dao.impl", 
		"com.myself.mybigdata.service.impl",
	})
public class RootConf {

}