package com.miniproject.service.member;

import com.miniproject.model.AutoLoginDTO;
import com.miniproject.model.LoginDTO;
import com.miniproject.model.MemberDTO;

public interface MemberService {
	
	// 아이디 중복검사
	boolean idIsDuplicate(String tmpUserId) throws Exception;

	// 회원가입 
	boolean saveMember(MemberDTO registerMember) throws Exception;

	// 로그인
	MemberDTO login(LoginDTO loginDTO) throws Exception;

	// 자동 로그인 정보저장
	boolean saveAutoLoginInfo(AutoLoginDTO autoLoginDTO) throws Exception;

	// 자동로그인 체크한 유저
	MemberDTO checkAutoLogin(String savedCookieSesId) throws Exception;
	
}
