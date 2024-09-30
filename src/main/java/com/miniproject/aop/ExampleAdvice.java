package com.miniproject.aop;


import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component // 스프링 컨테이너에게 객체를 만들어 관리하게 하는 어노테이션
@Aspect // 아래의 객체를 AOP객체로 사용할 것임을 명시하는 어노테이션
public class ExampleAdvice {
	
	@Before("execution(public * com.miniproject.service.hboard.HBoardServiceImpl.saveBoard(..))")
	public void startAOP(JoinPoint jp) {
		log.info("================== AOP 시작 ==================");
		
		Object[] params = jp.getArgs();
		log.info("startAOP jp.getArgs() = {}", Arrays.toString(params));
	}
	
	
	@After("execution(public * com.miniproject.service.hboard.HBoardServiceImpl.saveBoard(..))")
	public void endAOP() {
		log.info("================== AOP 끝 ==================");		
	}
	
}
