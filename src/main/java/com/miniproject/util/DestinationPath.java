package com.miniproject.util;

import javax.servlet.http.HttpServletRequest;

// 로그인을 하지 않았을 때, 로그인 페이지로 이동되기 전에, 원래 가려던 페이지 경로를 저장하는 객체
public class DestinationPath {

	private String destPath;
	private String query;
	
	public void setDestPath(HttpServletRequest req) {
		// 글작성 요청시 uri : /hboard/saveBoard
		// 글수정, 삭제시 uri : /hboard/modifyBoard?boardNo=330 (쿼리스트링이 있다)
		String uri = req.getRequestURI()+"";
		
		
		destPath = uri;
		query = req.getQueryString();
		
		if(query != null) {	// 쿼리가 있는경우
			req.getSession().setAttribute("destPath", destPath+"?"+query);
		} else {
			req.getSession().setAttribute("destPath", destPath);
		}
		req.getSession().setAttribute("query", query);
		
	}
	
}
