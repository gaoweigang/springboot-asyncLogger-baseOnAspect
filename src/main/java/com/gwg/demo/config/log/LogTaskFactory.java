package com.gwg.demo.config.log;

import com.gwg.demo.config.SpringContextHolder;
import com.gwg.demo.domain.SysOperationLog;
import com.gwg.demo.mapper.SysOperationLogMapper;
import com.gwg.demo.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * 日志操作任务创建工厂
 * 
 */
@Slf4j
public class LogTaskFactory {

	private static SysOperationLogMapper operationLogMapper = SpringContextHolder.getBean(SysOperationLogMapper.class);

	public static TimerTask bussinessLog(final String logType, final String userId, final String bussinessName, final String clazzName,
			final String methodName, final String request, final String response, final String msg) {
		return new TimerTask() {
			@Override
			public void run() {
				SysOperationLog operationLog = LogFactory.createOperationLog(logType, userId,
						bussinessName, clazzName, methodName, request, response, msg);
				try {
					operationLogMapper.insert(operationLog);
				} catch (Exception e) {
					log.error("创建业务日志异常!", e);
				}
			}
		};
	}

	public static TimerTask exceptionLog(final String logType, final String userId, final String bussinessName, final String clazzName,
										 final String methodName, final String request, final String response, final Exception exception) {
		return new TimerTask() {
			@Override
			public void run() {
				String msg = ExceptionUtil.convertExceptionMsg(exception);
				SysOperationLog operationLog = LogFactory.createOperationLog(logType, userId,
						bussinessName, clazzName, methodName, request, response, msg);
				try {
					operationLogMapper.insert(operationLog);
				} catch (Exception e) {
					log.error("创建异常日志异常!", e);
				}
			}
		};
	}


}
