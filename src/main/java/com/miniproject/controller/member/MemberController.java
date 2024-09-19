package com.miniproject.controller.member;

import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
	public void sendMailAuthCode(@RequestParam("tmpUserEmail") String tmpUserEmail) throws AddressException, MessagingException {
		log.info("tmpUserEmail : " + tmpUserEmail + "로 이메일을 보내자");
		String authCode = UUID.randomUUID().toString();
		
		try {
			new SendMailService().sendMail(tmpUserEmail, authCode);
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
}
