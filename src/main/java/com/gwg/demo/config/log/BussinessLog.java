package com.gwg.demo.config.log;

import java.lang.annotation.*;

/**
 * 标记需要做业务日志的方法
 * 
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface BussinessLog {

	/**
	 * 日志类型
	 */
	LogType value();

	/**
	 * 日志名称
	 */
	String logName() default "";



}
