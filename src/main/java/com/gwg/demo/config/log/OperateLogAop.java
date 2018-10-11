package com.gwg.demo.config.log;

import com.alibaba.fastjson.JSONObject;
import com.houbank.telesale.back.common.log.LogManager;
import com.houbank.telesale.back.common.log.LogObjectHolder;
import com.houbank.telesale.back.common.log.factory.LogTaskFactory;
import com.houbank.telesale.back.model.user.UserModel;
import com.houbank.telesale.back.shiro.BaseShiroConfig;
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
 * @author rensong
 * @version 创建时间：2018年7月25日 下午6:28:52 类说明
 */
@Aspect
@Component
@Slf4j
public class OperateLogAop {

	@Pointcut(value = "@annotation(com.houbank.telesale.back.common.annotation.BussinessLog)")
	public void cutService() {

	}

	@Around("cutService()")
	public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {

		// 先执行业务
		Object result = point.proceed();

		try {
			handle(point);
		} catch (Exception e) {
			log.error("日志记录出错!", e);
		}

		return result;
	}

	private void handle(ProceedingJoinPoint point) throws Exception {

		// 获取拦截的方法名
		Signature sig = point.getSignature();
		MethodSignature msig = null;
		if (!(sig instanceof MethodSignature)) {
			throw new IllegalArgumentException("该注解只能用于方法");
		}
		msig = (MethodSignature) sig;
		Object target = point.getTarget();
		Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
		String methodName = currentMethod.getName();

		// 如果当前用户未登录，不做日志
		UserModel user = BaseShiroConfig.acquireUserInfo();
		if (null == user) {
			return;
		}
		// 获取拦截方法的参数
		String className = point.getTarget().getClass().getName();
		Object[] params = point.getArgs();

		// 获取操作名称
		BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
		String bussinessName = annotation.value();

		// 涉及到修改
		String msg = "";
		if (bussinessName.indexOf("修改") != -1 || bussinessName.indexOf("编辑") != -1
				|| bussinessName.indexOf("更新") != -1) {
			Object obj1 = LogObjectHolder.me().getObject();
			log.info("旧值：{}，新值：{}", JSONObject.toJSONString(obj1), JSONObject.toJSONString(params));
			msg = "name=" + user.getName() + ",旧值=" + JSONObject.toJSONString(obj1) + ",新值="
					+ JSONObject.toJSONString(params);
		} else {
			msg = "name=" + user.getName() + ",param=" + JSONObject.toJSONString(params);
		}

		LogManager.me()
				.executeLog(LogTaskFactory.bussinessLog(user.getUsername(), bussinessName, className, methodName, msg));
	}
}
