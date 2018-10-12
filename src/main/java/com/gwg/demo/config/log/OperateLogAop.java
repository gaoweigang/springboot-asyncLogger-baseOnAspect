package com.gwg.demo.config.log;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 */
@Aspect
@Component
@Slf4j
public class OperateLogAop {

	@Pointcut(value = "@annotation(com.gwg.demo.config.log.BussinessLog)")
	public void cutService() {

	}

	@Around("cutService()")
	public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
		LogType logType = null;//日志类型
		String logName = null;//日志名称
		String className = null;//类名
		String methodName = null;//方法名
		Object params = null; //方法入参
		Object result = null;//方法出参

		try {
			// 获取拦截的方法名
			Signature sig = point.getSignature();
			MethodSignature msig = null;
			if (!(sig instanceof MethodSignature)) {
				throw new IllegalArgumentException("该注解只能用于方法");
			}
			msig = (MethodSignature) sig;
			Object target = point.getTarget();
			Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
			methodName = currentMethod.getName();

			// 获取拦截方法的参数
			className = point.getTarget().getClass().getName();
			params = point.getArgs();

			// 获取操作名称
			BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
			logName = annotation.logName();
			logType = annotation.value();

			// 涉及到修改
			String msg = "";
			if (logType == LogType.ADD || logType == LogType.DELETE || logType == LogType.UPDATE) {
				Object obj1 = LogObjectHolder.me().getObject();//注意使用
				log.info("旧值：{}，新值：{}", JSONObject.toJSONString(obj1), JSONObject.toJSONString(params));
				msg = "旧值=" + JSONObject.toJSONString(obj1) + ",新值=" + JSONObject.toJSONString(params);
			} else if(logType == LogType.QUERY) {
				msg = JSONObject.toJSONString(params);
			}

			// 先执行业务
			result = point.proceed();

			LogManager.me()
					.executeLog(LogTaskFactory.bussinessLog( logType.message, null, logName, className, methodName, JSONObject.toJSONString(params), JSONObject.toJSONString(result), msg));
		} catch (Exception e) {
			log.error("日志记录出错!", e);
			LogManager.me()
					.executeLog(LogTaskFactory.exceptionLog( LogType.EXCEPTION.message, null, logName, className, methodName, JSONObject.toJSONString(params), JSONObject.toJSONString(result), e));
		}

		return result;
	}

}
