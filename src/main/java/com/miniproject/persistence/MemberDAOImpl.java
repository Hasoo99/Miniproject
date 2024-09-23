package com.miniproject.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproject.model.LoginDTO;
import com.miniproject.model.MemberDTO;

@Repository
public class MemberDAOImpl implements MemberDAO{

	@Inject
	SqlSession ses;
	
	private String ns = "com.miniproject.mappers.memberMapper.";
	
	@Override
	public int updateUserPoint(String userId) throws Exception {

		return ses.update(ns + "updateUserPoint", userId);
	}

	@Override
	public int selectDuplicateId(String tmpUserId) throws Exception {
		return ses.selectOne(ns+"selectUserId", tmpUserId);
	}

	@Override
	public int insertMember(MemberDTO registerMember) {
		return ses.insert(ns + "insertMember", registerMember);
	}

	@Override
	public MemberDTO login(LoginDTO loginDTO) {
		return ses.selectOne(ns + "loginWithLoginDTO", loginDTO);
	}

}
