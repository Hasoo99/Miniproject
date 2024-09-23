package com.miniproject.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.miniproject.model.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession ses = request.getSession();
		log.info("preHandler()");
		//
		if (ses.getAttribute("loginMember") != null) {
			log.info("cleaning loginMember......");
			ses.removeAttribute("loginMember");
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("postHandler() 호출...");
		
		if (request.getMethod().toUpperCase().equals("POST")) {
			Map<String, Object> model = modelAndView.getModel();
			MemberDTO loginMember = (MemberDTO) model.get("loginMember");

			log.info("loginMember : " + loginMember);

			if (loginMember != null) { // 로그인 성공
				// 세션에 로그인한 유저의 정보를 저장
				HttpSession ses = request.getSession();
				ses.setAttribute("loginMember", loginMember);
				
				// 로그인하기 이전에 저장한 경로가 있다면, 그쪽으로 가고
				// 없다면 "/" 로 페이지로 이동
				String tmp = (String) ses.getAttribute("destPath");
				String query = (String) ses.getAttribute("query");
				log.info("요청 query : {}", query);
				log.info("가야할 곳 : {} " , tmp);
				response.sendRedirect((tmp == null) ? "/" : tmp);
				
			} else { // 로그인 실패
				response.sendRedirect("/member/login?status=fail");
			}
		}

	}

}
