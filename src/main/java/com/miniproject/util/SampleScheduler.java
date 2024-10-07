package com.miniproject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.miniproject.service.member.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableScheduling
public class SampleScheduler {
	
	@Autowired
	private MemberService mService;
	
	
//	@Scheduled(cron = "0/5 * * * * *") // 5초 마다
//	@Scheduled(cron = "* * * * * *") // 매초마다
	@Scheduled(cron = "2/5 49 * ? OCT WED") //  2초부터 5초마다 49분부터 10월 수요일부터 실행
	
	
	public void  sampleSchdule() {
		log.info("======================= Scheduling =========================");
		log.info(mService.toString());
	}
}
