package com.miniproject.aop;

import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.miniproject.model.LoginDTO;
import com.miniproject.model.MemberDTO;
import com.miniproject.persistence.MemberDAO;

import lombok.extern.slf4j.Slf4j;

// 로그인하는 멤버의 정보를 얻어내어 그 정보를 csv로 저장 하자
@Slf4j
@Component
@Aspect
//@EnableAspectJAutoProxy
public class LoginlogAOP {
	
	private String logContent;
	private static Map<String, Integer> loginAttemptCount = new ConcurrentHashMap<String, Integer>();
	
	@Autowired
	private MemberDAO mDao;
	
	@Around("execution(* com.miniproject.service.member.MemberServiceImpl.login(..))")
	public Object betweenMemberLogin(ProceedingJoinPoint pjp) throws Throwable {
		log.info("======================= AOP Before ======================");
		// MemberServiceImpl.login메서드가 실행되기 이전에 수행할 부분
		
		Object[] params = pjp.getArgs();
		log.info("AOP Before : pjp.getArgs() = {} ", Arrays.toString(params)); 
		
		LoginDTO loginDTO = (LoginDTO) params[0];
		String who = loginDTO.getUserId();
		log.info("{} 가 로그인하려고 함....", who);
		
		// 로그인로그에 기록할 사항
		long curTime = System.currentTimeMillis();
		String loginTime = new Date(curTime).toString();
		
		String ipAddr = loginDTO.getIpAddr(); 
		
		Calendar now = Calendar.getInstance();// 현재 날짜 시간 객체
	 	String year = now.get(Calendar.YEAR) + ""; // 2024 
	 	String month = year + new DecimalFormat("00").format(now.get(Calendar.MONTH) + 1);   // 202410
	 	String when = month + new DecimalFormat("00").format(now.get(Calendar.DATE));  // 20241002
	 	
	 	this.logContent = loginTime + "," + who + "," + ipAddr;
		
		
		log.info("======================= AOP After ======================");
		Object result = pjp.proceed(); // login()메서드의 반환값
		
		if (result == null) {
			this.logContent += ",login fail";
			
			// 5회 모두 실패하면, isLock = 'Y'로 MemberDTO를 수정해서 반환하자.
			result = saveLoginFail(who, result); 
		} else {
			log.info(" === AOP After === {}", result.toString());
			this.logContent += ",login success";
			removeLoginFail(who);
		}

		// csv로 저장
		// /resources/logs : 로그 저장 경로
		ServletRequestAttributes requestAttr =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttr.getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/logs");
		log.info("realPath = {}", realPath);
		
		FileWriter fw = new FileWriter(realPath + File.separator + "log_" + when + ".csv" , true);
		fw.write(this.logContent + "\n");
		log.info("로그인 기록저장 완료....");
		
		fw.flush();
		fw.close();
		
		return result; // serviceImpl.login메서드를 호출한 곳으로 반환
	}

	private Object saveLoginFail(String failUserId, Object result) throws Exception {
		log.info("loginAttemptCount : {} ", loginAttemptCount);
		
		if (loginAttemptCount.containsKey(failUserId)) {
			// 기존에 로그인 실패한 적이 있다
			int failCount = loginAttemptCount.get(failUserId);
			++failCount; // 로그인 시도 횟수 1증가
			
			if (failCount <= 4 ) {
				loginAttemptCount.put(failUserId, failCount);
			} else if (failCount == 5) {
				// 로그인 시도 횟수(5번으로 제한)를 초과한 유저이므로 계정을 잠궈야 한다.
				log.info("계정 잠그자.....");
				mDao.updateAccountLock(failUserId); // 계정 잠금 (isLock = 'Y')
				removeLoginFail(failUserId); // 계정 잠금 후 이전에 로그인 기록 삭제
				
				MemberDTO memberDTO = (MemberDTO) result;
				result = memberDTO.builder().userId(failUserId).isLock("Y").build();
			}
			
//			log.info("userId = {}, failCount = {}", failUserId, failCount);
		
		} else {
			// 기존에 로그인 실패한 기록이 없다. 
			loginAttemptCount.put(failUserId, 1);
//			log.info("userId = {}, failCount = {}", failUserId, loginAttemptCount.get(failUserId));
		}
	
		outputLoginAttempCount();
		
		return result;
	}

	private void removeLoginFail(String who) {
		if (loginAttemptCount.containsKey(who)) {
			loginAttemptCount.remove(who);
		}
		
		outputLoginAttempCount();
	}

	private void outputLoginAttempCount() {
		log.info("======================= 로그인 시도 횟수 ========================");
		Set<String>  keys = loginAttemptCount.keySet();
		for (String k : keys) {
			log.info("userId = {}, attemptCount = {}", k, loginAttemptCount.get(k));
		}
		log.info("==================================================================");
		
	}
	
	
}
