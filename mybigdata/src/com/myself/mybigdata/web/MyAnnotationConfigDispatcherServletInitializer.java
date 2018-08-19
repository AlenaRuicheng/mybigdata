package com.myself.mybigdata.web;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.myself.mybigdata.conf.RootConf;

/**
 *	Servlet初始化类
 */
public class MyAnnotationConfigDispatcherServletInitializer
		extends AbstractAnnotationConfigDispatcherServletInitializer {

	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {RootConf.class};
	}

	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {WebConf.class};
	}

	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

}
