package com.miniproject.exception.controller;

import java.sql.SQLException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CommonException {

	@ExceptionHandler
	public String sqlExceptionProcess(SQLException se) {
		log.error("SQL Exception 발생 : " + se.getMessage());
		
		return "/commonError";
	}
	
	@ExceptionHandler // 모든 예외 처리
	public String exceptionHandling(Exception e, Model model) {
		
		model.addAttribute("errorMsg", e.getMessage());
		model.addAttribute("errorStack", e.getStackTrace());
		return "/commonError";
	}
}
