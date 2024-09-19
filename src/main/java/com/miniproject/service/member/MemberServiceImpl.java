package com.miniproject.service.member;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.miniproject.persistence.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService{

	@Inject
	private MemberDAO mDao;
	
	@Override
	public boolean idIsDuplicate(String tmpUserId) throws Exception {
		boolean result = false; // 중복되지않는다.
		if (mDao.selectDuplicateId(tmpUserId) == 1) {
			result = true; // 중복된다!
		}
		
		return result;
	}

}
