package com.miniproject.controller.member;

import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.miniproject.model.MyResponseWithoutData;
import com.miniproject.service.member.MemberService;
import com.miniproject.util.SendMailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/member")
@Controller
public class MemberController {

	@Inject
	private MemberService mService;

	@RequestMapping("/register")
	public void showRegisterForm() {
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void registerMember() {

	}

	@RequestMapping(value = "/isDuplicate", method = RequestMethod.POST)
	public ResponseEntity<MyResponseWithoutData> idIsDuplicate(@RequestParam("tmpUserId") String tmpUserId) {
		log.info("tmpUserId : " + tmpUserId + "가 중복되는지 확인하자");

		MyResponseWithoutData json = null;
		ResponseEntity<MyResponseWithoutData> result = null;

		try {
			if (mService.idIsDuplicate(tmpUserId)) {
				// 아이디가 중복됨
				json = new MyResponseWithoutData(200, tmpUserId, "duplicate");
			} else {
				// 아이디가 중복되지 않음
				json = new MyResponseWithoutData(200, tmpUserId, "not duplicate");
			}
			result = new ResponseEntity<MyResponseWithoutData>(json, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping(value = "/callSendMail")
	public ResponseEntity<String> sendMailAuthCode(@RequestParam("tmpUserEmail") String tmpUserEmail, HttpSession session) throws AddressException, MessagingException {
		log.info("tmpUserEmail : " + tmpUserEmail + "로 이메일을 보내자");
		String authCode = UUID.randomUUID().toString();
		String  result = null;
		System.out.println("인증코드 확인용: " + authCode);
		
		try {
			new SendMailService().sendMail(tmpUserEmail, authCode);
			// 인증코드를 세션 객체에 저장
			session.setAttribute("authCode", authCode);
			result = "success";
			
		} catch (IOException e) {
			e.printStackTrace();
			result = "fail";
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/checkAuthCode", method = RequestMethod.POST)
	public ResponseEntity<String> checkAuthCode(@RequestParam("tmpUserAuthCode") String tmpUserAuthCode, HttpSession session) {
		System.out.println("tmpUserAuthCode : " + tmpUserAuthCode + "와 우리가 보낸 코드가 같은지 확인");
		System.out.println("session에 저장된 인증코드 : " + session.getAttribute("authCode"));
		
		String result = "fail";
		
		if(session.getAttribute("authCode") != null) {
			String setAuthCode = session.getAttribute("authCode")+"";
			if(tmpUserAuthCode.equals(setAuthCode)) {
				result = "success";
				System.out.println("일치");
			} else {
				System.out.println("불일치");
			}
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
