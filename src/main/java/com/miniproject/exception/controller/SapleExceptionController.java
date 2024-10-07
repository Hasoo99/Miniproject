package com.miniproject.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.model.MyResponseWithoutData;
import com.miniproject.model.SapleDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SapleExceptionController {

//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(IllegalArgumentException.class)
//	public MyResponseWithoutData illegalExceptionHandler(IllegalArgumentException e) {
//		
//		log.error("exceptionHandler 예외 발생", e);
//		return new MyResponseWithoutData(400, "", e.getMessage());
//	}
//	
//	@ExceptionHandler
//	public ResponseEntity<MyResponseWithoutData> myExceptionhandler(Exception e) {
//		log.error("exception예외 발생", e);
//		
//		return new ResponseEntity<MyResponseWithoutData>(new MyResponseWithoutData(500, "", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//	}
	
	
	@GetMapping("/exc/{id}")
	public SapleDTO genException(@PathVariable("id") String id) {
		
		if(id.equals("ex")) {
			throw new IllegalArgumentException("잘못된 아규먼트 예외");
		}
		
		if(id.equals("ex1")) {
			throw new RuntimeException("런타임 예외");			
		}
		
		return new SapleDTO(id, "name");
	}
	
}
