package com.miniproject.persistence;

import com.miniproject.model.AutoLoginDTO;
import com.miniproject.model.LoginDTO;
import com.miniproject.model.MemberDTO;

public interface MemberDAO {
	// 유저의 userPoint를 수정하는 메서드
	int updateUserPoint(String userId) throws Exception;

	// 아이디 중복체크
	int selectDuplicateId(String tmpUserId) throws Exception;

	// 회원가입
	int insertMember(MemberDTO registerMember);

	// 로그인
	MemberDTO login(LoginDTO loginDTO);

	// 자동로그인 정보저장
	int updateAutoLoginInfo(AutoLoginDTO autoLoginDTO) throws Exception;

	// 자동로그인 유저 확인
	MemberDTO checkAutoLogin(String savedCookieSesId) throws Exception;

	// 댓글 작성시 포인트 업데이트
	int updateUserPointComment(String commenter) throws Exception;

	// 유저의 계정을 잠금
	int updateAccountLock(String userId) throws Exception;
}
