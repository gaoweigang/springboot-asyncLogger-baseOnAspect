package com.gwg.demo.config.log;

import com.gwg.demo.config.SpringContextHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

/**
 * 被修改bean临时存放地方
 */
@Component
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class LogObjectHolder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object object = null;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public static LogObjectHolder me() {
		LogObjectHolder bean = SpringContextHolder.getBean(LogObjectHolder.class);
		return bean;
	}
}
