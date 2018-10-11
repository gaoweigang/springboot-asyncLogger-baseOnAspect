package com.gwg.demo.config.log;

import com.houbank.telesale.back.constant.enums.LogSucceed;
import com.houbank.telesale.back.constant.enums.LogType;
import com.houbank.telesale.back.domain.BackSysOperationLogWithBLOBs;
import com.houbank.telesale.back.mapper.BackSysOperationLogMapper;
import com.houbank.telesale.back.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.TimerTask;

/**
 * 日志操作任务创建工厂
 * 
 */
@Slf4j
public class LogTaskFactory {

	private static BackSysOperationLogMapper operationLogMapper = SpringContextHolder
			.getBean(BackSysOperationLogMapper.class);

	public static TimerTask bussinessLog(final String userId, final String bussinessName, final String clazzName,
			final String methodName, final String msg) {
		return new TimerTask() {
			@Override
			public void run() {
				BackSysOperationLogWithBLOBs operationLog = LogFactory.createOperationLog(LogType.BUSSINESS, userId,
						bussinessName, clazzName, methodName, msg, LogSucceed.SUCCESS);
				try {
					operationLogMapper.insert(operationLog);
				} catch (Exception e) {
					log.error("创建业务日志异常!", e);
				}
			}
		};
	}

	public static TimerTask exceptionLog(final String userId, final Exception exception) {
		return new TimerTask() {
			@Override
			public void run() {
				String msg = getExceptionMsg(exception);
				BackSysOperationLogWithBLOBs operationLog = LogFactory.createOperationLog(LogType.EXCEPTION, userId, "",
						null, null, msg, LogSucceed.FAIL);
				try {
					operationLogMapper.insert(operationLog);
				} catch (Exception e) {
					log.error("创建异常日志异常!", e);
				}
			}
		};
	}

	/**
	 * 获取异常的具体信息
	 * 
	 * @param e
	 * @return
	 */
	public static String getExceptionMsg(Exception e) {
		StringWriter sw = new StringWriter();
		try {
			e.printStackTrace(new PrintWriter(sw));
		} finally {
			try {
				sw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return sw.getBuffer().toString().replaceAll("\\$", "T");
	}
}
