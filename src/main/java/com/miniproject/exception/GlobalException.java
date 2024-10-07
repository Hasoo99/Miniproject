package com.miniproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.miniproject.model.MyResponseWithoutData;

import lombok.extern.slf4j.Slf4j;


@Slf4j
//@RestControllerAdvice
public class GlobalException {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public MyResponseWithoutData illegalExceptionHandler(IllegalArgumentException e) {
		
		log.error("exceptionHandler 예외 발생", e);
		return new MyResponseWithoutData(400, "", e.getMessage());
	}
	
	@ExceptionHandler
	public ResponseEntity<MyResponseWithoutData> myExceptionhandler(Exception e) {
		log.error("exception예외 발생", e);
		
		return new ResponseEntity<MyResponseWithoutData>(new MyResponseWithoutData(500, "", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
