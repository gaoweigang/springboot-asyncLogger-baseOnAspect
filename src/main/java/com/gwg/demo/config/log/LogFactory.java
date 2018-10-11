package com.gwg.demo.config.log;

import com.houbank.telesale.back.constant.enums.LogSucceed;
import com.houbank.telesale.back.constant.enums.LogType;
import com.houbank.telesale.back.domain.BackSysOperationLogWithBLOBs;

import java.util.Date;

/**
 * 日志对象创建工厂
 * 
 */
public class LogFactory {

	/**
	 * 创建操作日志
	 */
	public static BackSysOperationLogWithBLOBs createOperationLog(LogType logType, String userId, String bussinessName,
			String clazzName, String methodName, String msg, LogSucceed succeed) {
		BackSysOperationLogWithBLOBs operationLog = new BackSysOperationLogWithBLOBs();
		operationLog.setLogType(logType.getMessage());
		operationLog.setLogName(bussinessName);
		operationLog.setUserId(userId);
		operationLog.setClassname(clazzName);
		operationLog.setMethod(methodName);
		operationLog.setCreateDate(new Date());
		operationLog.setSucceed(succeed.getMessage());
		operationLog.setMessage(msg);
		return operationLog;
	}
}
