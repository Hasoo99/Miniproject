package com.miniproject.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InterceptorExample extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 지정된 컨트롤러의 동작 이전에 가로채는 역할로 사용되는 메서드
		log.info("preHandle()동작......");
		log.info(handler.toString());
		HandlerMethod method = (HandlerMethod) handler;
		Method methodobj = method.getMethod();
		
		log.info("Bean: " + method.getBean());
		log.info("Method: " + methodobj);
		
		return true; // 해당 컨트롤러단의 메서드로 제어가 돌아간다.
//		return false; // 해당 컨트롤러단의 메서드로 제어가 돌아가지 않는다.
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("postHandle()동작......");
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("afterCompletion()동작......");
		super.afterCompletion(request, response, handler, ex);
	}

}
