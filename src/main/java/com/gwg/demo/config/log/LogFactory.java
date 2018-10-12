package com.gwg.demo.config.log;

import com.gwg.demo.domain.SysOperationLog;

import java.util.Date;

/**
 * 日志对象创建工厂
 */
public class LogFactory {

	/**
	 * 创建操作日志
	 */
	public static SysOperationLog createOperationLog(String logType, String userId, String bussinessName,
													 String clazzName, String methodName, String request, String response, String msg) {
		SysOperationLog sysOperationLog = new SysOperationLog();
		sysOperationLog.setLogType(logType); //日志类型
		sysOperationLog.setLogName(bussinessName);
		sysOperationLog.setUserId(userId);
		sysOperationLog.setClassname(clazzName);
		sysOperationLog.setMethod(methodName);
		sysOperationLog.setRequest(request);//方法入参
		sysOperationLog.setResponse(response);//方法出参
		sysOperationLog.setCreateDate(new Date());
		sysOperationLog.setMessage(msg);
		return sysOperationLog;
	}
}
