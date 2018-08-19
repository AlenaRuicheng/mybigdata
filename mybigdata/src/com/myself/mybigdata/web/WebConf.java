package com.myself.mybigdata.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Component
@EnableWebMvc
@ComponentScan(basePackages= {"com.myself.mybigdata.web.controller"})
public class WebConf extends WebMvcConfigurerAdapter{
	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = 
				new InternalResourceViewResolver("/jsps/", ".jsp");
		return internalResourceViewResolver;
	} 
	
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
