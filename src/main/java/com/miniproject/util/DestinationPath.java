package com.miniproject.util;

import javax.servlet.http.HttpServletRequest;

import com.mysql.cj.Query;
import com.mysql.cj.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

// 로그인을 하지 않았을 때, 로그인 페이지로 이동되기 전에, 원래 가려던 페이지 경로를 저장하는 객체
@Slf4j
public class DestinationPath {

	private String destPath;
	private String query;
	
	public void setDestPath(HttpServletRequest req) {
		// 글작성 요청시 uri : /hboard/saveBoard
		// 글수정, 삭제시 uri : /hboard/modifyBoard?boardNo=330 (쿼리스트링이 있다)
		String uri = req.getRequestURI()+"";
		
		
//		destPath = uri;
		query = req.getQueryString();
		System.out.println(query);
		
//		if(StringUtils.isNullOrEmpty(query)) {
//			// 쿼리스트링이 없다
//			this.destPath = uri;
//		} else {
//			// 쿼리스트링이 있다
//			this.destPath = uri + "?" + query;
//		}
		
		
//		if(query != null) {	// 쿼리가 있는경우
//			req.getSession().setAttribute("destPath", destPath+"?"+query);
//		} else {
//			req.getSession().setAttribute("destPath", destPath);
//		}
	
		destPath = (query == null) ? uri : uri + "?" + query;
		
		req.getSession().setAttribute("destPath", destPath);
//		req.getSession().setAttribute("query", query);
		log.info("destPath = [{}]", destPath);
	}
	
}
