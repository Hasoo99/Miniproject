package com.miniproject.interceptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.miniproject.service.hboard.HBoardService;
import com.miniproject.util.DestinationPath;

import lombok.extern.slf4j.Slf4j;

//로그인 인증이 필요한 페이지에서 클라이언트가 현재 로그인 되어 있는지 아닌지 검사한다.
// 로그인 인증이 필요한 페이지 (글작성, 글수정, 글삭제 등)
// 로그인이 되어 있지 않으면, 로그인하도록 하고
// 로그인이 되어 있다면, 클라이언트가 원래 하려던 작업(목적지)을 하도록 해야 한다.

@Slf4j
public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Inject
	private HBoardService service;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("preHandle()동작 중......");

		HttpSession ses = request.getSession();
		new DestinationPath().setDestPath(request); // 로그인하기 전 호출했던 페이지를 세션에 저장

		if (ses.getAttribute("loginMember") == null) { // 로그인하지 않았다
			log.info("preHandel() = [{}]", "로그인 하지 않았다");			
			
			response.sendRedirect("/member/login"); // -> 로그인페이지로 끌려감
		} else { // 로그인을 했다 -> 글작성 페이지로 이동
			log.info("preHandel() = [{}]", "로그인 되어있다.");
			
			// 수정이나 삭제 페이지에서 왔다면 그 글에 대한 수정/삭제(본인글)인지 확인
			
		}


		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("postHandel()동작 중......");
	}

}
