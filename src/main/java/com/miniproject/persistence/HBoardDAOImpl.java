package com.miniproject.persistence;

import java.util.List;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.miniproject.model.HBoardVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository // 아래의 클래스가 DAO객체임을 명시
public class HBoardDAOImpl implements HBoardDAO {

	//@Inject
	//private HBoardDAO bDao;
	
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.miniproject.mappers.hboardMapper.";
	
	@Override
	public List<HBoardVO> selectAllBoard() {
		
		log.info("Here is HBoardDAOImpl....");
		
		return ses.selectList(ns + "getAllHBoard");
	}

}
