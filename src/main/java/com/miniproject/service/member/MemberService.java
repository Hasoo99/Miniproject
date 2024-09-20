package com.miniproject.service.member;

import com.miniproject.model.MemberDTO;

public interface MemberService {
	
	// 아이디 중복검사
	boolean idIsDuplicate(String tmpUserId) throws Exception;

	// 
	boolean saveMember(MemberDTO registerMember) throws Exception;
	
}
