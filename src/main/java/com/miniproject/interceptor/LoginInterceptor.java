package com.miniproject.interceptor;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.sql.Timestamp;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.miniproject.model.AutoLoginDTO;
import com.miniproject.model.MemberDTO;
import com.miniproject.service.member.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Inject
	private MemberService service;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession ses = request.getSession();
		log.info("preHandler()");

		boolean isLoginPageShow = false;

		if (ses.getAttribute("loginMember") != null) {
			log.info("cleaning loginMember......");
			ses.removeAttribute("loginMember");
		}

		Cookie autoLoginCookie = WebUtils.getCookie(request, "al");

		log.info("autoLoginCookie : {}", autoLoginCookie);

		if (request.getMethod().toUpperCase().equals("GET")) { // GET방식 요청일 때만 수행

			if (request.getParameter("redirectUrl") != null) {
				//댓글 작성하려다가 로그인하러 끌려옴
				log.info("댓글 작성하려다가 끌려옴");
				String uri = request.getParameter("redirectUrl");
				int boardNo = Integer.parseInt(request.getParameter("boardNo"));
				ses.setAttribute("destPath", "/cboard/viewBoard?boardNo=" + boardNo);
			}

			if (autoLoginCookie != null) {// 쿠키를 검사해서 자동로그인 쿠키가 존재하면
				String savedCookieSesId = autoLoginCookie.getValue();

				// DB에 다녀와서 자동로그인 체크한 유저를 자동로그인 시켜야 한다.
				MemberDTO autoLoginUser = service.checkAutoLogin(savedCookieSesId);
				System.out.println(autoLoginUser.toString());

				ses.setAttribute("loginMember", autoLoginUser);

				String dp = (String) ses.getAttribute("destPath");
				response.sendRedirect((dp != null ? dp : "/"));

				isLoginPageShow = false;
			} else { // 자동로그인 쿠키가 없는 경우
				// 로그인 하지 않은 경우 -> 로그인 페이지 보여준다.
				if (ses.getAttribute("loginMember") == null) {
					log.info("쿠키가 없고, 로그인 하지 않은 경우 로그인 페이지 보여준다.");
					isLoginPageShow = true;
				}
				// 로그인 한 경우 -> 로그인 페이지 보여주지 않는다.

			}
		} else if (request.getMethod().toUpperCase().equals("POST")) { // 로그인 버튼을 눌렀을 때
			isLoginPageShow = true;
		}
		return isLoginPageShow;
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

				// 자동로그인을 체크한 유저라면.....
				System.out.println("remember : " + request.getParameter("remember"));
				// 체크했다면 request.getParameter() = on, 체크하지 않으면 null
				if (request.getParameter("remember") != null) {
					log.info("자동 로그인 유저입니다");
					saveAutoLoginInfo(request, response);
				}

				// 로그인하기 이전에 저장한 경로가 있다면, 그쪽으로 가고
				// 없다면 "/" 로 페이지로 이동
				String tmp = (String) ses.getAttribute("destPath");
				String query = (String) ses.getAttribute("query");
				log.info("요청 query : {}", query);
				log.info("가야할 곳 : {} ", tmp);
				response.sendRedirect((tmp == null) ? "/" : tmp);

			} else { // 로그인 실패
				response.sendRedirect("/member/login?status=fail");
			}
		}

	}

	private void saveAutoLoginInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 자동로그인을 체크한 유저의 컬럼에 세션값과 만료일 DB저장
		// 쿠키생성
		String sesId = request.getSession().getId();
		MemberDTO loginMember = (MemberDTO) request.getSession().getAttribute("loginMember");
		String loginUserId = loginMember.getUserId();
		Timestamp allimit = new Timestamp(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000));

		if (service.saveAutoLoginInfo(new AutoLoginDTO(loginUserId, sesId, allimit))) {
			// 쿠키 생성 - 저장
			System.out.println("쿠키 저장");
			Cookie autoLoginCookie = new Cookie("al", sesId);
			autoLoginCookie.setMaxAge(7 * 24 * 60 * 60); // 일주일 동안 쿠키 유지 (초 seconds단위)
			autoLoginCookie.setPath("/"); // 쿠키가 저장될 경로 설정

			response.addCookie(autoLoginCookie);
		}
	}

}
